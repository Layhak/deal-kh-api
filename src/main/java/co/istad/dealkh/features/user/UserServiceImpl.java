package co.istad.dealkh.features.user;

import co.istad.dealkh.domain.Role;
import co.istad.dealkh.domain.User;
import co.istad.dealkh.domain.json.Image;
import co.istad.dealkh.features.role.RoleRepository;
import co.istad.dealkh.features.user.dto.UserProfileResponse;
import co.istad.dealkh.features.user.dto.UserRequest;
import co.istad.dealkh.features.user.dto.UserResponse;
import co.istad.dealkh.mapper.UserMapper;
import co.istad.dealkh.paging.PageResponse;
import co.istad.dealkh.paging.Pagination;
import co.istad.dealkh.specification.filter.UserFilter;
import co.istad.dealkh.specification.filter.UserSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final RoleRepository roleRepository;

    @Override
    public UserResponse getById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User has not been found!"));
        return userMapper.mapToUserResponse(user);
    }

    @Override
    public PageResponse<UserResponse> getAllUsers(int page, int size, String field, String order, Map<String, String> params) {
        UserFilter userFilter = new UserFilter();
        int pageSize = Pagination.page_limit;
        page = Pagination.page_number;
        size = pageSize;

        if (params.containsKey("username")) {
            String username = params.get("username");
            userFilter.setUsername(username);
        }
        if (params.containsKey("email")) {
            String email = params.get("email");
            userFilter.setEmail(email);
        }
        if (params.containsKey("phone")) {
            String phone = params.get("phone");
            userFilter.setPhone(phone);
        }
        if (params.containsKey("role")) {
            String role = params.get("role");
            userFilter.setRole(role);
        }
        if (params.containsKey("status")) {
            String status = params.get("status");
            userFilter.setStatus(status);
        }
        if (params.containsKey("gender")) {
            String gender = params.get("gender");
            userFilter.setGender(gender);
        }

        List<String> validFields = Arrays.asList("username", "email", "dob", "createdAt", "updatedAt");
        if (field == null || field.isEmpty() || !validFields.contains(field)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Field must be id, username, email, or dob");
        }
        if (order != null && !order.equals("asc") && !order.equals("desc")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Order must be asc or desc");
        }
        UserSpecification specification = new UserSpecification(userFilter);

        Pageable pageable = Pagination.getPageable(page, size, Sort.by(Sort.Direction.fromString(order), field));

        Page<UserResponse> users = userRepository.findAll(specification, pageable).map(userMapper::mapToUserResponse);
        return new PageResponse<>(users);
    }

    @Override
    public UserProfileResponse getUserProfile(Long id) {
        return null;
    }

    @Override
    public UserResponse createUser(UserRequest userRequest) {
        if (userRepository.existsByUsername(userRequest.username())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Username already exist ! Try another one ");
        }
        if (userRepository.existsByEmail(userRequest.email())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Email already token ! Try another one ");
        }
        Role role = roleRepository.findByName(userRequest.role())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Role has not been found!"));
        User newUser = userMapper.mapRequestToUser(userRequest);
        newUser.setIsDisabled(false);
        newUser.setCreatedAt(LocalDateTime.now());
        newUser.setUpdatedAt(LocalDateTime.now());
//        newUser.setPassword(new BCryptPasswordEncoder().encode(newUser.getPassword()));

        newUser.setRole(role);
        userRepository.save(newUser);
        return userMapper.mapToUserResponse(newUser);
    }

    @Override
    public UserResponse updateUser(Long id, UserRequest userRequest) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User has not been found!"));
        if (userRepository.existsByUsername(userRequest.username()) && !user.getUsername().equals(userRequest.username())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Username already exist ! Try another one ");
        }
        if (userRepository.existsByEmail(userRequest.email()) && !user.getEmail().equals(userRequest.email())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Email already token ! Try another one ");
        }
        Role role = roleRepository.findByName(userRequest.role())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Role has not been found!"));
        user.setRole(role);
        userRepository.save(user);
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

    @Override
    public List<UserResponse> getAllUsersByStatus(String status) {
        List<User> users = new ArrayList<>();
        if (status.equalsIgnoreCase("enabled") || status.equalsIgnoreCase("enable")) {
            users = userRepository.findAllByIsDisabledFalse();
        } else if (status.equalsIgnoreCase("disabled") || status.equalsIgnoreCase("disable")) {
            users = userRepository.findAllByIsDisabledTrue();
        }
        return users.stream().map(userMapper::mapToUserResponse).collect(Collectors.toList());
    }

    @Override
    public UserResponse updateUserImage(Long id, String imageUrl, String description) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        Image imageInfo = new Image();
        imageInfo.setUrl(imageUrl);
        imageInfo.setDescription(description);

        List<Image> images = user.getImage();
        if (images == null) {
            images = new ArrayList<>();
        }
        images.add(imageInfo);
        user.setImage(images);

        userRepository.save(user);
        return userMapper.mapToUserResponse(user);
    }

    @Override
    public boolean existsById(Long id) {
        return userRepository.existsById(id);
    }
}
