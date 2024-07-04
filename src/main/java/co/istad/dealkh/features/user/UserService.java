package co.istad.dealkh.features.user;

import co.istad.dealkh.features.user.dto.*;
import co.istad.dealkh.paging.PageResponse;

import java.util.List;
import java.util.Map;

public interface UserService {

    UserResponse getByUsername(String username);

    PageResponse<UserResponse> getAllUsers(int page, int size, String field, String order, Map<String, String> params);

    void createUser(UserCreateRequest userCreateRequest);

    UserResponse updateUser(String username, UserUpdateRequest userUpdateRequest);

    void deleteUser(String username);

    UserCoverResponse getUserCover(String username);

    void deleteUserCover(String username,  UserCoverRequest userCoverRequest);

    UserCoverResponse uploadUserCover(String username, UserCoverRequest userCoverRequest);

    UserProfileResponse getUserProfile(String username);

    void deleteUserProfile(String username, String profile);

    UserProfileResponse uploadUserProfile(String username, UserProfileRequest userProfileRequest);

    void updatePassword(String username, String oldPassword, UserUpdatePasswordRequest userUpdatePasswordRequest);

    UserResponse disableUser(String username);

    UserResponse enableUser(String username);

    UserResponse addRole(String auth, String username, UserRoleRequest userRoleRequest);

    UserResponse removeRole(String auth, String username, UserRoleRequest userRoleRequest);

    PageResponse<UserResponse> getAllBuyer(int page, int size, String field, String order);

    PageResponse<UserResponse> getAllSeller(int page, int size, String field, String order);
    PageResponse<UserResponse> getAllAdmin(int page, int size, String field, String order);

    void verifyEmail(String token);

    List<SellerResponse> getAllOwnerShop(String slug);

}
