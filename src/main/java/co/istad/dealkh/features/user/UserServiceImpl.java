package co.istad.dealkh.features.user;

import co.istad.dealkh.domain.Role;
import co.istad.dealkh.domain.User;
import co.istad.dealkh.domain.json.Image;
import co.istad.dealkh.features.role.RoleRepository;
import co.istad.dealkh.features.user.dto.*;
import co.istad.dealkh.mapper.UserMapper;
import co.istad.dealkh.paging.PageResponse;
import co.istad.dealkh.paging.Pagination;
import co.istad.dealkh.specification.filter.UserFilter;
import co.istad.dealkh.specification.filter.UserSpecification;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final VerificationService verificationService;
    //    private final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    @Value("${app.frontend.verify-url}")
    private String verifyUrl;

    private void extractFilterParams(@NotNull UserFilter userFilter, Map<String, String> params) {
        userFilter.setUsername(params.get("username"));
        userFilter.setEmail(params.get("email"));
        userFilter.setPhone(params.get("phone"));
        userFilter.setRole(params.get("role"));
        userFilter.setStatus(params.get("status"));
        userFilter.setGender(params.get("gender"));
    }

    private void validateSortingParams(String field, String order) {
        List<String> validFields = Arrays.asList("username", "email", "dob", "createdAt", "updatedAt");

        if (field == null || field.isEmpty() || !validFields.contains(field)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Field must be username, username, email, dob, createdAt, updatedAt");
        }
        if (order != null && !order.equalsIgnoreCase("asc") && !order.equalsIgnoreCase("desc")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Order must be asc or desc");
        }
    }

    @Override
    public UserResponse getByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User has not been found!"));
        return userMapper.mapToUserResponse(user);
    }

    @Override
    public PageResponse<UserResponse> getAllUsers(int page, int size, String field, String order, Map<String, String> params) {
        UserFilter userFilter = new UserFilter();
        extractFilterParams(userFilter, params);

        validateSortingParams(field, order);

        Specification<User> specification = new UserSpecification(userFilter);

        Pageable pageable = Pagination.getPageable(page, size, Sort.by(Sort.Direction.fromString(order), field));

        Page<UserResponse> users = userRepository.findAll(specification, pageable).map(userMapper::mapToUserResponse);
        return new PageResponse<>(users);
    }


    @Override
    public void createUser(UserCreateRequest userRequest) {
        if (userRepository.existsByUsername(userRequest.username())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Username already exists: " + userRequest.username());
        }
        if (userRepository.existsByEmail(userRequest.email())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already taken! Try another one.");
        }
        if (userRepository.existsByPhoneNumber(userRequest.phoneNumber())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Phone number already taken! Try another one.");
        }
        if (!userRequest.confirmedPassword().equals(userRequest.password())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password does not match!");
        }

        User newUser = userMapper.mapCreateRequestToUser(userRequest);

        LocalDate dob;
        try {
            dob = LocalDate.parse(userRequest.dob(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        } catch (DateTimeParseException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid date format for dob");
        }

        newUser.setCovers(new ArrayList<>());
        newUser.setSocialMedias(new ArrayList<>());
        newUser.setIsDisabled(false);
        newUser.setIsVerified(false);  // Set to false initially
        newUser.setEmail(userRequest.email());
        newUser.setDob(dob);
        newUser.setCreatedAt(LocalDateTime.now());
        newUser.setUpdatedAt(LocalDateTime.now());
        newUser.setRoles(Set.of(roleRepository.findByName("BUYER").orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Role not found!")
        )));
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));

        // Generate verification token
        String token = UUID.randomUUID().toString();
        newUser.setVerificationToken(token);

        newUser.setTokenExpiryDate(LocalTime.now().plusHours(24).withNano(0)); // Token valid for 24 hours

        userRepository.save(newUser);

        // Send verification email
        verificationService.sendVerificationEmail(newUser, token, verifyUrl);
    }


    @Override
    public UserResponse updateUser(String username, UserUpdateRequest userUpdateRequest) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User has not been found!"));

        if (userRepository.existsByUsername(userUpdateRequest.username()) && !user.getUsername().equals(userUpdateRequest.username())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Username already exists! Try another one.");
        }

        if (userRepository.existsByPhoneNumber(userUpdateRequest.phoneNumber()) && !user.getPhoneNumber().equals(userUpdateRequest.phoneNumber())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Phone number already taken! Try another one.");
        }
        LocalDate dob;
        try {
            dob = LocalDate.parse(userUpdateRequest.dob(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        } catch (DateTimeParseException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid date format for dob");
        }
        user.setDob(dob);
        user.setUpdatedAt(LocalDateTime.now());
        user.setUpdatedBy(username);

        // Update other user fields
        userMapper.mapUpdateRequestToUser(user, userUpdateRequest);

        // Save the updated user
        userRepository.save(user);

        // Return the updated user response
        return userMapper.mapToUserResponse(user);
    }


    @Override
    public void deleteUser(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        // Remove user entries from the dk_user_shops join table
        userRepository.deleteUserShopsByUserId(user.getId());

        // Now delete the user
        userRepository.delete(user);
    }

    @Override
    public UserCoverResponse getUserCover(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User has not been found!"));
        return userMapper.mapToUserCoverResponse(user);
    }

    @Override
    public void deleteUserCover(String username, UserCoverRequest userCoverRequest) {
        // Fetch the user by username
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User has not been found!"));

        // Check if the cover exists
        boolean coverExists = user.getCovers().stream()
                .anyMatch(img -> img.getUrl().equals(userCoverRequest.cover()));

        // If the cover does not exist, throw a BAD_REQUEST exception
        if (!coverExists) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cover not found!");
        }

        // Filter out the cover to be deleted
        List<Image> filteredImages = user.getCovers().stream()
                .filter(img -> !img.getUrl().equals(userCoverRequest.cover()))
                .collect(Collectors.toList());

        // Set the filtered covers back to the user
        user.setCovers(filteredImages);

        // Save the updated user
        userRepository.save(user);
    }


    @Override
    public UserCoverResponse uploadUserCover(String username, UserCoverRequest userCoverRequest) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User has not been found!"));

        List<Image> existingImages = user.getCovers();

        if (user.getCovers().isEmpty()) {
            user.setCovers(new ArrayList<>());
        }
        if (userCoverRequest.cover().isEmpty() || userCoverRequest.cover().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cover image is required");
        }
        Image newImage = new Image(userCoverRequest.cover());

        existingImages.add(newImage);
        user.setCovers(existingImages);
        user.setUpdatedAt(LocalDateTime.now());
        user.setUpdatedBy(username);

        userRepository.save(user);

        return userMapper.mapToUserCoverResponse(user);
    }

    @Override
    public UserProfileResponse getUserProfile(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User has not been found!"));
        return userMapper.mapToUserProfileResponse(user);
    }

    @Override
    public void deleteUserProfile(String username, String profile) {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User has not been found!"));
//
//        if (userRepository.findByUsernameAndProfile(username, profile).isEmpty()) {
//            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You're not allowed to delete this profile");
//        }

        user.setUpdatedAt(LocalDateTime.now());
        user.setUpdatedBy(username);
        user.setProfile(null);
        userRepository.save(user);
    }

    @Override
    public UserProfileResponse uploadUserProfile(String username, UserProfileRequest userProfileRequest) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User has not been found!"));
        user.setUpdatedAt(LocalDateTime.now());
        user.setUpdatedBy(username);
        user.setProfile(userProfileRequest.profile());
        userRepository.save(user);
        return userMapper.mapToUserProfileResponse(user);

    }


    @Override
    public void updatePassword(String username, String oldPassword, UserUpdatePasswordRequest userUpdatePasswordRequest) {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User has not been found!"));

        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Old password is incorrect.");
        }

        if (!userUpdatePasswordRequest.newPassword().equals(userUpdatePasswordRequest.newPasswordConfirmation())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "New passwords do not match.");
        }

        user.setUpdatedAt(LocalDateTime.now());
        user.setUpdatedBy(username);

        user.setPassword(passwordEncoder.encode(userUpdatePasswordRequest.newPassword()));

        userRepository.save(user);
    }

    @Override
    public UserResponse disableUser(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User has not been found!"));
        user.setIsDisabled(true);
        userRepository.save(user);
        return userMapper.mapToUserResponse(user);
    }

    @Override
    public UserResponse enableUser(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User has not been found!"));
        user.setIsDisabled(false);
        user.setUpdatedAt(LocalDateTime.now());
        user.setUpdatedBy(username);
        userRepository.save(user);
        return userMapper.mapToUserResponse(user);
    }

    @Override
    public UserResponse addRole(String auth, String username, UserRoleRequest userRoleRequest) {

        // Check if user exists
        User superAdmin = userRepository.findByUsername(auth)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User has not been found!"));


        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User has not been found!"));

        if (superAdmin.getRoles().stream().noneMatch(role -> role.getName().equals("SUPER_ADMIN"))) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only SUPER_ADMIN can add roles");
        }

        if (user.getRoles().stream().anyMatch(role -> role.getName().equals(userRoleRequest.role()))) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User already has the role: " + userRoleRequest.role());
        }

        Role newRole = roleRepository.findByName(userRoleRequest.role())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Role not found: " + userRoleRequest.role()));


        if (newRole.getName().equals("SUPER_ADMIN")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot add SUPER_ADMIN role");
        }

        user.getRoles().add(newRole);

        user.setUpdatedAt(LocalDateTime.now());
        user.setUpdatedBy(auth);
        userRepository.save(user);

        return userMapper.mapToUserResponse(user);
    }

    @Override
    public UserResponse removeRole(String auth, String username, UserRoleRequest userRoleRequest) {

        // check authenticate user role
        User superAdmin = userRepository.findByUsername(auth)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User has not been found!"));

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User has not been found!"));

        if (superAdmin.getRoles().stream().noneMatch(role -> role.getName().equals("SUPER_ADMIN"))) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only SUPER_ADMIN can remove roles");
        }

        Role roleToRemove = roleRepository.findByName(userRoleRequest.role())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Role has not been found!"));

        // Check if the user has the role that needs to be removed
        if (user.getRoles().stream().noneMatch(role -> role.getName().equals(userRoleRequest.role()))) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Role not found");
        }

        // Prevent removal if the user only has one role left
        if (user.getRoles().size() == 1) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User has only one role");
        }

        user.getRoles().remove(roleToRemove);
        user.setUpdatedAt(LocalDateTime.now());
        user.setUpdatedBy(username);
        userRepository.save(user);

        return userMapper.mapToUserResponse(user);
    }


    @Override
    public PageResponse<UserResponse> getAllBuyer(int page, int size, String field, String order) {

        Pageable pageable = Pagination.getPageable(page, size, Sort.by(Sort.Direction.fromString(order), field));

        Page<UserResponse> buyers = userRepository.findAllUserByRoles_Name("BUYER", pageable).map(userMapper::mapToUserResponse);
        if (buyers.getContent().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No buyers found");
        }
        return new PageResponse<>(buyers);
    }

    @Override
    public PageResponse<UserResponse> getAllSeller(int page, int size, String field, String order) {

        Pageable pageable = Pagination.getPageable(page, size, Sort.by(Sort.Direction.fromString(order), field));

        Page<UserResponse> sellers = userRepository.findAllUserByRoles_Name("SELLER", pageable).map(userMapper::mapToUserResponse);
        if (sellers.getContent().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No buyers found");
        }
        return new PageResponse<>(sellers);
    }

    @Override
    public PageResponse<UserResponse> getAllAdmin(int page, int size, String field, String order) {
        Pageable pageable = Pagination.getPageable(page, size, Sort.by(Sort.Direction.fromString(order), field));

        Page<UserResponse> admins = userRepository.findAllUserByRoles_Name("ADMIN", pageable).map(userMapper::mapToUserResponse);
        if (admins.getContent().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No admins found");
        }
        return new PageResponse<>(admins);
    }

    @Override
    public void verifyEmail(String token) {
        User user = userRepository.findByVerificationToken(token)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Invalid token"));

        if (user.getTokenExpiryDate().isAfter(LocalTime.now())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Token expired");
        }

        user.setIsVerified(true);
        user.setVerificationToken(null);  // Clear the token
        user.setTokenExpiryDate(null);    // Clear the expiry date
        userRepository.save(user);    // Clear the expiry date
    }

    @Override
    public List<SellerResponse> getAllOwnerShop(String slug) {
        return userRepository.findAllByShopsSlug(slug)
                .stream()
                .map(userMapper::mapToSellerResponse)
                .toList();
    }

    @Override
    public void resendVerificationToken(String email) {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with email: " + email);
        }

        User user = optionalUser.get();

        if (user.getIsVerified()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User is already verified.");
        }

        // Generate a new verification token
        String token = UUID.randomUUID().toString();
        user.setVerificationToken(token);
        user.setTokenExpiryDate(LocalTime.now().plusHours(24)); // Token valid for 24 hours
        user.setLastEmailSentAt(LocalDateTime.now());

        // Save the user with the new token and expiry date
        userRepository.save(user);

        // Send verification email
        verificationService.sendVerificationEmail(user, token, verifyUrl);
    }
}
