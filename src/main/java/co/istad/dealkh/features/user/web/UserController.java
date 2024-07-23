package co.istad.dealkh.features.user.web;

import co.istad.dealkh.base.BaseResponse;
import co.istad.dealkh.features.user.UserService;
import co.istad.dealkh.features.user.dto.*;
import co.istad.dealkh.paging.PageResponse;
import co.istad.dealkh.security.CustomUserDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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

    public BaseResponse<PageResponse<UserResponse>> getAllUsers(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "25") int size,
            @RequestParam(defaultValue = "id") String field,
            @RequestParam(defaultValue = "asc") String order,
            @RequestParam Map<String,
                    String> params
    ) {
        // Add default parameters if not present
        params.putIfAbsent("status", "enable");

        return BaseResponse.<PageResponse<UserResponse>>ok("Successfully retrieved users!").setPayload(userService.getAllUsers(page, size, field, order, params));
    }

    @GetMapping("/{username}")
    BaseResponse<UserResponse> getUserById(@PathVariable String username) {
        return BaseResponse.<UserResponse>ok("Successfully retrieve user with username:" + username).setPayload(userService.getByUsername(username));
    }

    @DeleteMapping("/{username}")
        BaseResponse<?> deleteUser(@PathVariable String username) {
        userService.deleteUser(username);
        return BaseResponse.ok("Delete user success").setPayload(new ArrayList<>());
    }

    @PutMapping
    BaseResponse<UserResponse> updateUser(@AuthenticationPrincipal CustomUserDetails customUserDetails, @RequestBody UserUpdateRequest updateRequest) {
        return BaseResponse.<UserResponse>ok("update success").setPayload(userService.updateUser(customUserDetails.getUsername(), updateRequest));
    }

    @PatchMapping("/{username}/disable")
        BaseResponse<UserResponse> disableUser(@PathVariable String username) {
        return BaseResponse.<UserResponse>ok("Successfully disable user").setPayload(userService.disableUser(username));
    }

    @PatchMapping("/{username}/enable")
        BaseResponse<UserResponse> enableUser(@PathVariable String username) {
        return BaseResponse.<UserResponse>ok("Successfully enable user").setPayload(userService.enableUser(username));
    }

    @GetMapping("/me")
        public BaseResponse<UserResponse> getCurrentUserInfo(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        String username = customUserDetails.getUsername();
        return BaseResponse.<UserResponse>ok("Success get current user info").setPayload(userService.getByUsername(username));
    }

    @GetMapping("/cover")
        public BaseResponse<UserCoverResponse> getUserCover(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        String username = customUserDetails.getUsername();
        return BaseResponse.<UserCoverResponse>ok("Success get user cover").setPayload(userService.getUserCover(username));
    }

    @PostMapping("/cover")
        public BaseResponse<UserCoverResponse> uploadUserCover(@AuthenticationPrincipal CustomUserDetails customUserDetails, @RequestBody UserCoverRequest userProfileRequest) {
        String username = customUserDetails.getUsername();
        return BaseResponse.<UserCoverResponse>ok("Success update user cover").setPayload(userService.uploadUserCover(username, userProfileRequest));
    }

    @DeleteMapping("/cover")
        public BaseResponse<?> deleteUserCover(@AuthenticationPrincipal CustomUserDetails customUserDetails, @RequestBody UserCoverRequest userCoverRequest) {
        String username = customUserDetails.getUsername();
        System.out.println(username);
        userService.deleteUserCover(username, userCoverRequest);
        return BaseResponse.ok("Successfully delete user cover")
                .setPayload("No content");

    }


    @GetMapping("/profile")
        public BaseResponse<UserProfileResponse> getUserProfile(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        String username = customUserDetails.getUsername();
        return BaseResponse.<UserProfileResponse>ok("Success get user profile").setPayload(userService.getUserProfile(username));
    }

    @PostMapping("/profile")
        public BaseResponse<UserProfileResponse> uploadUserProfile(@AuthenticationPrincipal CustomUserDetails customUserDetails, @RequestBody UserProfileRequest userProfileRequest) {
        String username = customUserDetails.getUsername();
        return BaseResponse.<UserProfileResponse>ok("Success update user profile").setPayload(userService.uploadUserProfile(username, userProfileRequest));
    }

    @DeleteMapping("/profile")
        public BaseResponse<?> deleteUserProfile(@AuthenticationPrincipal CustomUserDetails customUserDetails, @RequestBody String profile) {
        String username = customUserDetails.getUsername();
        System.out.println(username);
        userService.deleteUserProfile(username, profile);
        return BaseResponse.ok("Successfully delete user profile image")
                .setPayload("No content");

    }


    @PutMapping("/updatePassword/{oldPassword}")
        public BaseResponse<?> updatePassword(@AuthenticationPrincipal CustomUserDetails customUserDetails, @Valid @RequestBody UserUpdatePasswordRequest updatePasswordRequest
            , @PathVariable String oldPassword) {
        String username = customUserDetails.getUsername();
        userService.updatePassword(username, oldPassword, updatePasswordRequest);
        return BaseResponse.ok("Successfully update user password").setPayload(new ArrayList<>());
    }


    @PostMapping("/addRole/{username}")
        public BaseResponse<UserResponse> addRole(@AuthenticationPrincipal CustomUserDetails customUserDetails, @PathVariable String username, @RequestBody UserRoleRequest userRoleRequest) {
        //if user has  role admin or super admin
        return BaseResponse.<UserResponse>ok("Successfully add role to user").setPayload(userService.addRole(customUserDetails.getUsername(), username, userRoleRequest));
    }

    @DeleteMapping("/removeRole/{username}")
        public BaseResponse<UserResponse> removeRole(@AuthenticationPrincipal CustomUserDetails customUserDetails, @PathVariable String username, @RequestBody UserRoleRequest userRoleRequest) {
        return BaseResponse.<UserResponse>ok("Successfully remove role from user").setPayload(userService.removeRole(customUserDetails.getUsername(), username, userRoleRequest));
    }

    @GetMapping("/buyers")
        public BaseResponse<PageResponse<UserResponse>> getAllBuyer(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "25") int size,
            @RequestParam(defaultValue = "id") String field,
            @RequestParam(defaultValue = "asc") String order
    ) {
        return BaseResponse.<PageResponse<UserResponse>>ok("Successfully retrieve all buyers!")
                .setPayload(userService.getAllBuyer(page, size, field, order));
    }

    @GetMapping("/sellers")
        public BaseResponse<PageResponse<UserResponse>> getAllSeller(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "25") int size,
            @RequestParam(defaultValue = "id") String field,
            @RequestParam(defaultValue = "asc") String order
    ) {
        return BaseResponse.<PageResponse<UserResponse>>ok("Successfully retrieve all sellers!")
                .setPayload(userService.getAllSeller(page, size, field, order));
    }

    @GetMapping("/admins")
        public BaseResponse<PageResponse<UserResponse>> getAllAdmin(
                @RequestParam(defaultValue = "1") int page,
                @RequestParam(defaultValue = "25") int size,
                @RequestParam(defaultValue = "id") String field,
                @RequestParam(defaultValue = "asc") String order
    ) {
        return BaseResponse.<PageResponse<UserResponse>>ok("Successfully retrieve all admins!")
                .setPayload(userService.getAllAdmin(page, size, field, order));
    }

    @GetMapping("/{slug}/owners")
        public BaseResponse<List<SellerResponse>> getAllOwnerShop(@PathVariable String slug) {
        return BaseResponse.<List<SellerResponse>>ok("Successfully retrieve all owners!")
                .setPayload(userService.getAllOwnerShop(slug));
    }

    @PostMapping("/resend-verification-token")
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<Void> resendVerificationToken(@RequestParam("email") String email) {
        try {
            userService.resendVerificationToken(email);
            return BaseResponse.<Void>ok("Verification token resent successfully");
        } catch (ResponseStatusException e) {
            return BaseResponse.<Void>badRequest(e.getReason());
        } catch (Exception e) {
            return BaseResponse.<Void>badRequest("An error occurred while resending the verification token.");
        }
    }

    @PostMapping("/social")
        public BaseResponse<?> uploadSocialMedia(@AuthenticationPrincipal CustomUserDetails customUserDetails, @RequestBody UserSocialMediaRequest userSocialMediaRequest) {
        String username = customUserDetails.getUsername();
        userService.uploadSocialMedia(username, userSocialMediaRequest);
        return BaseResponse.ok("Successfully upload social media!");
    }
}
