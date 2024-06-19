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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

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
    public UserResponse createUser(UserCreateRequest userRequest) {
        if (userRepository.existsByUsername(userRequest.username())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Username already exists: " + userRequest.username());
        }
        if (userRepository.existsByEmail(userRequest.email())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Email already taken! Try another one.");
        }

        if (!userRequest.confirmedPassword().equals(userRequest.password())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password does not match!");
        }

        User newUser = userMapper.mapCreateRequestToUser(userRequest);
        // Convert the dob string to LocalDate
        LocalDate dob;
        try {
            dob = LocalDate.parse(userRequest.dob(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        } catch (DateTimeParseException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid date format for dob");
        }
        newUser.setImages(new ArrayList<>());
        newUser.setSocialMedias(new ArrayList<>());
        newUser.setIsDisabled(false);
        newUser.setEmail(userRequest.email());
        newUser.setDob(dob);
        newUser.setCreatedAt(LocalDateTime.now());
        newUser.setUpdatedAt(LocalDateTime.now());
        newUser.setRoles(Set.of(roleRepository.findByName("BUYER").orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Role not found!")
        )));
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));

        userRepository.save(newUser);
        return userMapper.mapToUserResponse(newUser);
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
        // Check if user exists and delete
        userRepository.findByUsername(username)
                .ifPresentOrElse(userRepository::delete,
                        () -> {
                            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User has not been found!");
                        });
    }

    @Override
    public UserProfileResponse getUserProfile(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User has not been found!"));
        return userMapper.mapToUserProfileResponse(user);
    }

    @Override
    public void deleteUserProfile(String username, String imageUrl) {
        // Fetch the user by username
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User has not been found!"));


        List<Image> filteredImages = user.getImages().stream()
                .filter(image -> !image.getUrl().equals(imageUrl))
                .collect(Collectors.toList());

        user.setUpdatedAt(LocalDateTime.now());
        user.setUpdatedBy(username);

        userRepository.save(user);
    }

    @Override
    public UserProfileResponse uploadUserProfile(String username, UserProfileRequest userProfileRequest) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User has not been found!"));

        List<Image> existingImages = user.getImages();

        if (user.getImages().isEmpty()) {
            user.setImages(new ArrayList<>());
        }
        Image newImage = new Image(userProfileRequest.imageUrl());

        existingImages.add(newImage);
        user.setImages(existingImages);
        user.setUpdatedAt(LocalDateTime.now());
        user.setUpdatedBy(username);

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
    public UserResponse addRole(String username, UserRoleRequest userRoleRequest) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User has not been found!"));

        Role newRole = roleRepository.findByName(userRoleRequest.role())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Role not found: " + userRoleRequest.role()));

        // Check if the user has the BUYER role and is trying to promote to ADMIN or SUPER_ADMIN
        if (user.getRoles().stream().anyMatch(role -> role.getName().equals("BUYER") || role.getName().equals("SELLER"))
                && (userRoleRequest.role().equals("ADMIN") || userRoleRequest.role().equals("SUPER_ADMIN"))) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User with BUYER or SELLER role cannot promote themselves to ADMIN or SUPER_ADMIN");
        }

        // Check if the user has the ADMIN role and is trying to promote to SUPER_ADMIN
        if (user.getRoles().stream().anyMatch(role -> role.getName().equals("ADMIN"))
                && userRoleRequest.role().equals("SUPER_ADMIN")) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User with ADMIN role cannot promote themselves to SUPER_ADMIN");
        }

        // Check if the user already has the role
        if (user.getRoles().stream().anyMatch(role -> role.getName().equals(userRoleRequest.role()))) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Role already exists");
        }

        // Check if the user does not have any of the required roles (ADMIN, SUPER_ADMIN, BUYER)
        if (user.getRoles().stream().noneMatch(role -> role.getName().equals("ADMIN") || role.getName().equals("SUPER_ADMIN") || role.getName().equals("BUYER") || role.getName().equals("SELLER"))) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User does not have the necessary role to add a new role");
        }

        user.getRoles().add(newRole);
        user.setUpdatedAt(LocalDateTime.now());
        user.setUpdatedBy(username);
        userRepository.save(user);

        return userMapper.mapToUserResponse(user);
    }


    @Override
    public UserResponse removeRole(String username, UserRoleRequest userRoleRequest) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User has not been found!"));

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

        // Check if the user attempting the removal has the BUYER role but not SUPER_ADMIN or ADMIN
        boolean isBuyerOnly = user.getRoles().stream().anyMatch(role -> role.getName().equals("BUYER")) &&
                user.getRoles().stream().noneMatch(role -> role.getName().equals("SUPER_ADMIN") || role.getName().equals("ADMIN"));

        // Check if the target user has SUPER_ADMIN or ADMIN roles
        User targetUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Target user has not been found!"));

        boolean isTargetSuperAdminOrAdmin = targetUser.getRoles().stream().anyMatch(role -> role.getName().equals("SUPER_ADMIN") || role.getName().equals("ADMIN"));

        if (isBuyerOnly && isTargetSuperAdminOrAdmin) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User with BUYER role cannot remove roles from a user with SUPER_ADMIN or ADMIN roles");
        }

        // Check if the user attempting the removal is not SUPER_ADMIN and is trying to remove a role from SUPER_ADMIN
        boolean isNotSuperAdmin = user.getRoles().stream().noneMatch(role -> role.getName().equals("SUPER_ADMIN"));
        boolean isTargetSuperAdmin = targetUser.getRoles().stream().anyMatch(role -> role.getName().equals("SUPER_ADMIN"));

        if (isNotSuperAdmin && isTargetSuperAdmin) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only SUPER_ADMIN can remove roles from another SUPER_ADMIN");
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

}
