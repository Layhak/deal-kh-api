package co.istad.dealkh.features.user;

import co.istad.dealkh.features.user.dto.*;
import co.istad.dealkh.paging.PageResponse;

import java.util.Map;

public interface UserService {

    UserResponse getById(Long id);

    PageResponse<UserResponse> getAllUsers(int page, int size, String field, String order, Map<String, String> params);


    UserResponse createUser(UserCreateRequest userCreateRequest);

    UserResponse updateUser(Long id, UserUpdateRequest userUpdateRequest);

    void deleteUser(Long id);

    UserProfileResponse getUserProfile(Long id);

    void deleteUserProfile(Long id, String imageUrl);

    UserProfileResponse uploadUserProfile(Long id, UserProfileRequest userProfileRequest);

    void updatePassword(Long id, UserUpdatePasswordRequest userUpdatePasswordRequest);

    void resetPassword(Long id, UserResetPasswordRequest userResetPasswordRequest);

    UserResponse disableUser(Long id);

    UserResponse enableUser(Long id);

    UserResponse addRole(Long id, UserRoleRequest userRoleRequest);

    UserResponse removerRole(Long id, UserRoleRequest userRoleRequest);

}
