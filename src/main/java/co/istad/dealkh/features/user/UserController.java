package co.istad.dealkh.features.user;

import co.istad.dealkh.base.BaseResponse;
import co.istad.dealkh.features.user.dto.*;
import co.istad.dealkh.paging.PageResponse;
import co.istad.dealkh.security.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;


    @GetMapping
    @Operation(
            summary = "Get all users with pagination, sorting, and filtering",
            description = "Default parameters: page=1, size=10, field=username, order=asc, status=enable")
    public BaseResponse<PageResponse<UserResponse>> getAllUsers(
            @Parameter(name = "page",
                    description = "Page number",
                    in = ParameterIn.QUERY,
                    schema = @Schema(defaultValue = "1")) @RequestParam(defaultValue = "1") int page,
            @Parameter(name = "size",
                    description = "Page size",
                    in = ParameterIn.QUERY,
                    schema = @Schema(defaultValue = "10")) @RequestParam(defaultValue = "10") int size,
            @Parameter(name = "field",
                    description = "Sort field",
                    in = ParameterIn.QUERY,
                    schema = @Schema(defaultValue = "username")) @RequestParam(defaultValue = "username") String field,
            @Parameter(name = "order",
                    description = "Sort order",
                    in = ParameterIn.QUERY,
                    schema = @Schema(defaultValue = "asc")) @RequestParam(defaultValue = "asc") String order,
            @Parameter(name = "params",
                    description = "Additional filter parameters (default: {\"status\": \"enable\"})",
                    in = ParameterIn.QUERY) @RequestParam Map<String,
                    String> params) {
        // Add default parameters if not present
        params.putIfAbsent("status", "enable");

        return BaseResponse.<PageResponse<UserResponse>>ok("Successfully retrieved users!").setPayload(userService.getAllUsers(page, size, field, order, params));
    }

    @GetMapping("/{id}")
    BaseResponse<UserResponse> getUserById(@PathVariable Long id) {
        return BaseResponse.<UserResponse>ok("Successfully retrieve user with id:" + id).setPayload(userService.getById(id));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete user")
    BaseResponse<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return BaseResponse.<Void>ok("Delete user success");
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update user", description = "Update user with id", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(schema = @Schema(implementation = UserUpdateRequest.class), examples = @ExampleObject(value = """
                         {
                           "dob": "2001-07-01",
                           "firstName": "Hom",
                           "gender": "female",
                           "lastName": "Pheakakvotey",
                           "location": "Phnom Penh",
                           "phoneNumber": "0987654321",
                           "username": "Votey",
                           "shopId": [
                           1
                           ]
                         }
            """))))
    BaseResponse<UserResponse> updateUser(@PathVariable Long id, @RequestBody UserUpdateRequest updateRequest) {
        return BaseResponse.<UserResponse>ok("update success").setPayload(userService.updateUser(id, updateRequest));
    }

    @PatchMapping("/{id}/disable")
    @Operation(summary = "Disable user")
    BaseResponse<UserResponse> disableUser(@PathVariable Long id) {
        return BaseResponse.<UserResponse>ok("Successfully disable user").setPayload(userService.disableUser(id));
    }

    @PatchMapping("/{id}/enable")
    @Operation(summary = "Enable user")
    BaseResponse<UserResponse> enableUser(@PathVariable Long id) {
        return BaseResponse.<UserResponse>ok("Successfully enable user").setPayload(userService.enableUser(id));
    }

    @GetMapping("/me")
    @Operation(summary = "Get current user info")
    public BaseResponse<UserResponse> getCurrentUserInfo(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        Long userId = customUserDetails.getUserId();
        return BaseResponse.<UserResponse>ok("Success get current user info").setPayload(userService.getById(userId));
    }

    @GetMapping("/profile")
    @Operation(summary = "Get user profile")
    public BaseResponse<UserProfileResponse> getUserProfile(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        Long userId = customUserDetails.getUserId();
        return BaseResponse.<UserProfileResponse>ok("Success get user profile").setPayload(userService.getUserProfile(userId));
    }

    @PostMapping("/profile")
    @Operation(summary = "Upload user profile")
    public BaseResponse<UserProfileResponse> uploadUserProfile(@AuthenticationPrincipal CustomUserDetails customUserDetails, @RequestBody UserProfileRequest userProfileRequest) {
        Long userId = customUserDetails.getUserId();
        return BaseResponse.<UserProfileResponse>ok("Success update user profile").setPayload(userService.uploadUserProfile(userId, userProfileRequest));
    }

    @DeleteMapping("/profile/deleteImage")
    @Operation(summary = "Delete user profile image")
    public BaseResponse<Void> deleteUserProfileImage(@AuthenticationPrincipal CustomUserDetails customUserDetails, @RequestParam String imageUrl) {
        Long userId = customUserDetails.getUserId();
        userService.deleteUserProfile(userId, imageUrl);
        return BaseResponse.ok("Successfully delete user profile image");

    }

    @PutMapping("/updatePassword")
    @Operation(summary = "Update user password")
    public BaseResponse<Void> updatePassword(@AuthenticationPrincipal CustomUserDetails customUserDetails, @RequestBody UserUpdatePasswordRequest updatePasswordRequest) {
        Long userId = customUserDetails.getUserId();
        userService.updatePassword(userId, updatePasswordRequest);
        return BaseResponse.ok("Successfully update user password");
    }

    @PutMapping("/resetPassword")
    @Operation(summary = "Reset user password")
    public BaseResponse<Void> resetPassword(@AuthenticationPrincipal CustomUserDetails customUserDetails, @RequestBody UserResetPasswordRequest userResetPasswordRequest) {
        Long userId = customUserDetails.getUserId();
        userService.resetPassword(userId, userResetPasswordRequest);
        return BaseResponse.ok("Successfully reset user password");
    }

    @PostMapping("/addRole")
    @Operation(summary = "Add role to user")
    public BaseResponse<UserResponse> addRole(@AuthenticationPrincipal CustomUserDetails customUserDetails, @RequestBody UserRoleRequest userRoleRequest) {
        Long userId = customUserDetails.getUserId();
        return BaseResponse.<UserResponse>ok("Successfully add role to user").setPayload(userService.addRole(userId, userRoleRequest));
    }

    @DeleteMapping("/removeRole")
    @Operation(summary = "Remove role from user")
    public BaseResponse<UserResponse> removeRole(@AuthenticationPrincipal CustomUserDetails customUserDetails, @RequestBody UserRoleRequest userRoleRequest) {
        Long userId = customUserDetails.getUserId();
        return BaseResponse.<UserResponse>ok("Successfully remove role from user").setPayload(userService.removerRole(userId, userRoleRequest));
    }
}
