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
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
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
    BaseResponse<?> deleteUser(@PathVariable String username) {
        userService.deleteUser(username);
        return BaseResponse.ok("Delete user success").setPayload(new ArrayList<>());
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

    @GetMapping("/cover")
    @Operation(summary = "Get user cover")
    public BaseResponse<UserCoverResponse> getUserCover(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        String username = customUserDetails.getUsername();
        return BaseResponse.<UserCoverResponse>ok("Success get user cover").setPayload(userService.getUserCover(username));
    }

    @PostMapping("/cover")
    @Operation(summary = "Upload user cover")
    public BaseResponse<UserCoverResponse> uploadUserCover(@AuthenticationPrincipal CustomUserDetails customUserDetails, @RequestBody UserCoverRequest userProfileRequest) {
        String username = customUserDetails.getUsername();
        return BaseResponse.<UserCoverResponse>ok("Success update user cover").setPayload(userService.uploadUserCover(username, userProfileRequest));
    }

    @DeleteMapping("/cover")
    @Operation(summary = "Delete user cover")
    public BaseResponse<?> deleteUserCover(@AuthenticationPrincipal CustomUserDetails customUserDetails, @RequestBody UserCoverRequest userCoverRequest) {
        String username = customUserDetails.getUsername();
        System.out.println(username);
        userService.deleteUserCover(username, userCoverRequest);
        return BaseResponse.ok("Successfully delete user cover")
                .setPayload("No content");

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

    @DeleteMapping("/profile")
    @Operation(summary = "Delete user profile image")
    public BaseResponse<?> deleteUserProfile(@AuthenticationPrincipal CustomUserDetails customUserDetails, @RequestBody String profile) {
        String username = customUserDetails.getUsername();
        System.out.println(username);
        userService.deleteUserProfile(username, profile);
        return BaseResponse.ok("Successfully delete user profile image")
                .setPayload("No content");

    }



    @PutMapping("/updatePassword/{oldPassword}")
    @Operation(summary = "Update user password")
    public BaseResponse<?> updatePassword(@AuthenticationPrincipal CustomUserDetails customUserDetails, @Valid @RequestBody UserUpdatePasswordRequest updatePasswordRequest
            , @PathVariable String oldPassword) {
        String username = customUserDetails.getUsername();
        userService.updatePassword(username, oldPassword, updatePasswordRequest);
        return BaseResponse.ok("Successfully update user password").setPayload(new ArrayList<>());
    }


    @PostMapping("/addRole/{username}")
    @Operation(summary = "Add role to user")
    public BaseResponse<UserResponse> addRole(@AuthenticationPrincipal CustomUserDetails customUserDetails, @PathVariable String username, @RequestBody UserRoleRequest userRoleRequest) {
        //if user has  role admin or super admin
        return BaseResponse.<UserResponse>ok("Successfully add role to user").setPayload(userService.addRole(customUserDetails.getUsername(), username, userRoleRequest));
    }

    @DeleteMapping("/removeRole/{username}")
    @Operation(summary = "Remove role from user")
    public BaseResponse<UserResponse> removeRole(@AuthenticationPrincipal CustomUserDetails customUserDetails, @PathVariable String username, @RequestBody UserRoleRequest userRoleRequest) {
        return BaseResponse.<UserResponse>ok("Successfully remove role from user").setPayload(userService.removeRole(customUserDetails.getUsername(), username, userRoleRequest));
    }

    @GetMapping("/buyers")
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
    ) {
        return BaseResponse.<PageResponse<UserResponse>>ok("Successfully retrieve all buyers!")
                .setPayload(userService.getAllBuyer(page, size, field, order));
    }

    @GetMapping("/sellers")
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
    ) {
        return BaseResponse.<PageResponse<UserResponse>>ok("Successfully retrieve all sellers!")
                .setPayload(userService.getAllSeller(page, size, field, order));
    }

    @GetMapping("/admins")
    @Operation(summary = "Get all admins")
    public BaseResponse<PageResponse<UserResponse>> getAllAdmin(
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
    ) {
        return BaseResponse.<PageResponse<UserResponse>>ok("Successfully retrieve all admins!")
                .setPayload(userService.getAllAdmin(page, size, field, order));
    }

    @GetMapping("/{slug}/owners")
    @Operation(summary = "Get all owner shop")
    public BaseResponse<List<SellerResponse>> getAllOwnerShop(@PathVariable String slug) {
        return BaseResponse.<List<SellerResponse>>ok("Successfully retrieve all owners!")
                .setPayload(userService.getAllOwnerShop(slug));
    }

}
