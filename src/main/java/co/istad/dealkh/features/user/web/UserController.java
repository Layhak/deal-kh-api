package co.istad.dealkh.features.user.web;

import co.istad.dealkh.base.BaseResponse;
import co.istad.dealkh.features.user.UserService;
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

/**
 * UserController is a controller for managing users.
 * It handles creating, retrieving, updating, and deleting users.
 *
 * <p>This class uses the following annotations:
 * <ul>
 * <li>{@link RestController} - Indicates that this class is a REST controller.</li>
 * <li>{@link RequiredArgsConstructor} - Generates a constructor with required arguments (final fields).</li>
 * <li>{@link RequestMapping} - Maps HTTP requests to handler methods of MVC and REST controllers.</li>
 * </ul>
 * </p>
 */
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
                    schema = @Schema(defaultValue = "25")) @RequestParam(defaultValue = "10") int size,
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

    @GetMapping("/{username}")
    BaseResponse<UserResponse> getUserById(@PathVariable String username) {
        return BaseResponse.<UserResponse>ok("Successfully retrieve user with username:" + username).setPayload(userService.getByUsername(username));
    }

    @DeleteMapping("/{username}")
    @Operation(summary = "Delete user")
    BaseResponse<Void> deleteUser(@PathVariable String username) {
        userService.deleteUser(username);
        return BaseResponse.<Void>ok("Delete user success");
    }

    @PutMapping("/{username}")
    @Operation(summary = "Update user", description = "Update user with username", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(schema = @Schema(implementation = UserUpdateRequest.class), examples = @ExampleObject(value = """
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
    BaseResponse<UserResponse> updateUser(@PathVariable String username, @RequestBody UserUpdateRequest updateRequest) {
        return BaseResponse.<UserResponse>ok("update success").setPayload(userService.updateUser(username, updateRequest));
    }

    @PatchMapping("/{username}/disable")
    @Operation(summary = "Disable user")
    BaseResponse<UserResponse> disableUser(@PathVariable String username) {
        return BaseResponse.<UserResponse>ok("Successfully disable user").setPayload(userService.disableUser(username));
    }

    @PatchMapping("/{username}/enable")
    @Operation(summary = "Enable user")
    BaseResponse<UserResponse> enableUser(@PathVariable String username) {
        return BaseResponse.<UserResponse>ok("Successfully enable user").setPayload(userService.enableUser(username));
    }

    @GetMapping("/me")
    @Operation(summary = "Get current user info")
    public BaseResponse<UserResponse> getCurrentUserInfo(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        String username = customUserDetails.getUsername();
        return BaseResponse.<UserResponse>ok("Success get current user info").setPayload(userService.getByUsername(username));
    }

    @GetMapping("/profile")
    @Operation(summary = "Get user profile")
    public BaseResponse<UserProfileResponse> getUserProfile(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        String username = customUserDetails.getUsername();
        return BaseResponse.<UserProfileResponse>ok("Success get user profile").setPayload(userService.getUserProfile(username));
    }

    @PostMapping("/profile")
    @Operation(summary = "Upload user profile")
    public BaseResponse<UserProfileResponse> uploadUserProfile(@AuthenticationPrincipal CustomUserDetails customUserDetails, @RequestBody UserProfileRequest userProfileRequest) {
        String username = customUserDetails.getUsername();
        return BaseResponse.<UserProfileResponse>ok("Success update user profile").setPayload(userService.uploadUserProfile(username, userProfileRequest));
    }

    @DeleteMapping("/profile/deleteImage")
    @Operation(summary = "Delete user profile image")
    public BaseResponse<Void> deleteUserProfileImage(@AuthenticationPrincipal CustomUserDetails customUserDetails, @RequestParam String imageUrl) {
        String username = customUserDetails.getUsername();
        userService.deleteUserProfile(username, imageUrl);
        return BaseResponse.ok("Successfully delete user profile image");

    }

    @PutMapping("/updatePassword")
    @Operation(summary = "Update user password")
    public BaseResponse<Void> updatePassword(@AuthenticationPrincipal CustomUserDetails customUserDetails, @RequestBody UserUpdatePasswordRequest updatePasswordRequest) {
        String username = customUserDetails.getUsername();
        userService.updatePassword(username, updatePasswordRequest);
        return BaseResponse.ok("Successfully update user password");
    }

    @PutMapping("/resetPassword")
    @Operation(summary = "Reset user password")
    public BaseResponse<Void> resetPassword(@AuthenticationPrincipal CustomUserDetails customUserDetails, @RequestBody UserResetPasswordRequest userResetPasswordRequest) {
        String username = customUserDetails.getUsername();
        userService.resetPassword(username, userResetPasswordRequest);
        return BaseResponse.ok("Successfully reset user password");
    }

    @PostMapping("/addRole")
    @Operation(summary = "Add role to user")
    public BaseResponse<UserResponse> addRole(@PathVariable String username, @RequestBody UserRoleRequest userRoleRequest) {
        //if user has  role admin or super admin
        return BaseResponse.<UserResponse>ok("Successfully add role to user").setPayload(userService.addRole(username, userRoleRequest));
    }

    @DeleteMapping("/removeRole")
    @Operation(summary = "Remove role from user")
    public BaseResponse<UserResponse> removeRole(@AuthenticationPrincipal CustomUserDetails customUserDetails, @RequestBody UserRoleRequest userRoleRequest) {
        String username = customUserDetails.getUsername();
        return BaseResponse.<UserResponse>ok("Successfully remove role from user").setPayload(userService.removerRole(username, userRoleRequest));
    }

    @GetMapping("/buyers)")
    @Operation(summary = "Get all buyers")
    public BaseResponse<PageResponse<UserResponse>> getAllBuyer(
            @Parameter(name = "page",
                    description = "Page number",
                    in = ParameterIn.QUERY,
                    schema = @Schema(defaultValue = "1")) @RequestParam(defaultValue = "1") int page,
            @Parameter(name = "size",
                    description = "Page size",
                    in = ParameterIn.QUERY,
                    schema = @Schema(defaultValue = "25")) @RequestParam(defaultValue = "25") int size,
            @Parameter(name = "field",
                    description = "Sort field",
                    in = ParameterIn.QUERY,
                    schema = @Schema(defaultValue = "username")) @RequestParam(defaultValue = "username") String field,
            @Parameter(name = "order",
                    description = "Sort order",
                    in = ParameterIn.QUERY,
                    schema = @Schema(defaultValue = "asc")) @RequestParam(defaultValue = "asc") String order
            )
    {
        return BaseResponse.<PageResponse<UserResponse>>ok("Successfully retrieve all buyers!")
                .setPayload(userService.getAllBuyer(page, size, field, order));
    }

    @GetMapping("/sellers)")
    @Operation(summary = "Get all sellers")
    public BaseResponse<PageResponse<UserResponse>> getAllSeller(
            @Parameter(name = "page",
                    description = "Page number",
                    in = ParameterIn.QUERY,
                    schema = @Schema(defaultValue = "1")) @RequestParam(defaultValue = "1") int page,
            @Parameter(name = "size",
                    description = "Page size",
                    in = ParameterIn.QUERY,
                    schema = @Schema(defaultValue = "25")) @RequestParam(defaultValue = "25") int size,
            @Parameter(name = "field",
                    description = "Sort field",
                    in = ParameterIn.QUERY,
                    schema = @Schema(defaultValue = "username")) @RequestParam(defaultValue = "username") String field,
            @Parameter(name = "order",
                    description = "Sort order",
                    in = ParameterIn.QUERY,
                    schema = @Schema(defaultValue = "asc")) @RequestParam(defaultValue = "asc") String order
            )
    {
        return BaseResponse.<PageResponse<UserResponse>>ok("Successfully retrieve all sellers!")
                .setPayload(userService.getAllSeller(page, size, field, order));
    }


}
