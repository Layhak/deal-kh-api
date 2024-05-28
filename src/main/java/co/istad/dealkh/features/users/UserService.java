package co.istad.dealkh.features.users;

import co.istad.dealkh.features.users.dto.UserProfileResponse;
import co.istad.dealkh.features.users.dto.UserRequest;
import co.istad.dealkh.features.users.dto.UserResponse;
import co.istad.dealkh.paging.PageResponse;

import java.util.List;

public interface UserService {

    UserResponse getById(Long id);

    PageResponse getAllUsers(int page, int size, String field, String order);

    UserProfileResponse getUserProfile(Long id);

    UserResponse createUser(UserRequest userRequest);

    UserResponse updateUser(Long id, UserRequest userRequest);

    void deleteUser(Long id);

    UserResponse updateUserImage(Long id, String imageUrl, String description);

    boolean existsById(Long id);

    UserResponse disableUser(Long id);

    UserResponse enableUser(Long id);

    //getAllEnabledUsers
    List<UserResponse> getAllUsersByStatus(String status);

}
