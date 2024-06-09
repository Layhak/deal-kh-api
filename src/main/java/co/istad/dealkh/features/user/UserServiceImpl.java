package co.istad.dealkh.features.user;

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

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
        List<String> validFields = Arrays.asList("id", "username", "email", "dob", "createdAt", "updatedAt");

        if (field == null || field.isEmpty() || !validFields.contains(field)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Field must be id, username, email, dob, createdAt, updatedAt");
        }
        if (order != null && !order.equalsIgnoreCase("asc") && !order.equalsIgnoreCase("desc")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Order must be asc or desc");
        }
    }

    @Override
    public UserResponse getById(Long id) {
        User user = userRepository.findById(id)
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
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Username already exists! Try another one.");
        }
        if (userRepository.existsByEmail(userRequest.email())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Email already taken! Try another one.");
        }

        User newUser = userMapper.mapCreateRequestToUser(userRequest);
        newUser.setIsDisabled(false);
        newUser.setEmail(userRequest.email());
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
    public UserResponse updateUser(Long id, UserUpdateRequest userUpdateRequest) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User has not been found!"));

        if (userRepository.existsByUsername(userUpdateRequest.username()) && !user.getUsername().equals(userUpdateRequest.username())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Username already exists! Try another one.");
        }

        // Update other user fields
        userMapper.mapUpdateRequestToUser(user, userUpdateRequest);

        // Save the updated user
        userRepository.save(user);

        // Return the updated user response
        return userMapper.mapToUserResponse(user);
    }


    @Override
    public void deleteUser(Long id) {
        // Check if user exists and delete
        userRepository.findById(id)
                .ifPresentOrElse(userRepository::delete,
                        () -> {
                            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User has not been found!");
                        });
    }

    @Override
    public UserProfileResponse getUserProfile(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User has not been found!"));
        return userMapper.mapToUserProfileResponse(user);
    }

    @Override
    public void deleteUserProfile(Long id, String imageUrl) {
        // Fetch the user by ID
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User has not been found!"));

        // Filter the images list to remove the image URL that matches the given imageUrl
        List<Image> filteredImages = user.getImages().stream()
                .filter(image -> !image.getUrl().equals(imageUrl))
                .collect(Collectors.toList());

        // Update the user's images list
        user.setImages(filteredImages);

        // Save the updated user
        userRepository.save(user);
    }

    @Override
    public UserProfileResponse uploadUserProfile(Long id, UserProfileRequest userProfileRequest) {
        // Fetch the user by ID
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User has not been found!"));

        // Get the existing images
        List<Image> existingImages = user.getImages();

        // Create a new Image object with the provided URL
        Image newImage = new Image(userProfileRequest.imageUrl());

        // Append the new image to the existing list
        existingImages.add(newImage);

        // Set the updated image list to the user
        user.setImages(existingImages);

        // Save the updated user
        userRepository.save(user);

        // Map the updated user entity to UserProfileResponse and return it
        return userMapper.mapToUserProfileResponse(user);
    }

    @Override
    public void updatePassword(Long id, UserUpdatePasswordRequest userUpdatePasswordRequest) {
        // Fetch the user by ID
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User has not been found!"));

        // Validate the old password
        if (!passwordEncoder.matches(userUpdatePasswordRequest.oldPassword(), user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Old password is incorrect.");
        }

        // Check that the new passwords match
        if (!userUpdatePasswordRequest.newPassword().equals(userUpdatePasswordRequest.newPasswordConfirmation())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "New passwords do not match.");
        }

        // Update the password
        user.setPassword(passwordEncoder.encode(userUpdatePasswordRequest.newPassword()));

        // Save the updated user
        userRepository.save(user);
    }

    @Override
    public void resetPassword(Long id, UserResetPasswordRequest userResetPasswordRequest) {
        // Fetch the user by ID
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User has not been found!"));

        // Check that the new passwords match
        if (!userResetPasswordRequest.newPassword().equals(userResetPasswordRequest.newPasswordConfirmation())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "New passwords do not match.");
        }

        // Update the password
        user.setPassword(passwordEncoder.encode(userResetPasswordRequest.newPassword()));

        // Save the updated user
        userRepository.save(user);
    }


    @Override
    public UserResponse disableUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User has not been found!"));
        user.setIsDisabled(true);
        userRepository.save(user);
        return userMapper.mapToUserResponse(user);
    }

    @Override
    public UserResponse enableUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User has not been found!"));
        user.setIsDisabled(false);
        userRepository.save(user);
        return userMapper.mapToUserResponse(user);
    }

}
