package co.istad.dealkh.features.user;

import co.istad.dealkh.base.BaseResponse;
import co.istad.dealkh.features.image.ImageService;
import co.istad.dealkh.features.user.dto.UserRequest;
import co.istad.dealkh.features.user.dto.UserResponse;
import co.istad.dealkh.paging.PageResponse;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;
    private final ImageService fileService;

    public UserController(UserService userService, @Qualifier("UserImages") ImageService fileService) {
        this.userService = userService;
        this.fileService = fileService;
    }

    @GetMapping
    public BaseResponse<PageResponse<UserResponse>> getAllUsers(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "2") int size,
            @RequestParam(defaultValue = "username") String field,
            @RequestParam(defaultValue = "asc") String order,
            @RequestParam Map<String, String> params
    ) {
        return BaseResponse.<PageResponse<UserResponse>>ok("Successfully retrieve data!!").setPayload(userService.getAllUsers(page, size, field, order, params));
    }

    @GetMapping("/{id}")
    BaseResponse<UserResponse> getUserById(@PathVariable Long id) {
        return BaseResponse.<UserResponse>ok("Successfully retrieve data with id:" + id).setPayload(userService.getById(id));
    }

    @PostMapping
    @Operation(summary = "Create a new user")
    BaseResponse<UserResponse> createUser(@RequestBody UserRequest userRequest) {
        return BaseResponse.<UserResponse>createSuccess("Successfully create new user!").setPayload(userService.createUser(userRequest));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete user")
    BaseResponse<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return BaseResponse.<Void>deleteSuccess("Delete user success");
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Update user")
    BaseResponse<UserResponse> updateUser(@PathVariable Long id, @RequestBody UserRequest userRequest) {
        return BaseResponse.<UserResponse>ok("update success").setPayload(userService.updateUser(id, userRequest));
    }

    @PatchMapping("/{id}/disable")
    @Operation(summary = "Disable user")
    BaseResponse<UserResponse> disableUser(@PathVariable Long id) {
        return BaseResponse.<UserResponse>ok("Success").setPayload(userService.disableUser(id));
    }

    @PatchMapping("/{id}/enable")
    @Operation(summary = "Enable user")
    BaseResponse<UserResponse> enableUser(@PathVariable Long id) {
        return BaseResponse.<UserResponse>ok("Success").setPayload(userService.enableUser(id));
    }

    @GetMapping("/status/{status}")
    @Operation(summary = "Get all users by status")
    BaseResponse<List<UserResponse>> getAllUsersByStatus(@PathVariable String status) {
        return BaseResponse.<List<UserResponse>>ok("Success").setPayload(userService.getAllUsersByStatus(status));
    }

    @PostMapping(value = "/{id}/profile/upload/multiple", consumes = "multipart/form-data")
    @Operation(summary = "Upload multiple profile pictures and update user")
    @ResponseStatus(HttpStatus.CREATED)
    public BaseResponse<UserResponse> uploadMultipleProfileImages(
            @PathVariable Long id,
            @RequestPart("files") List<MultipartFile> files,
            @RequestPart("descriptions") List<String> descriptions,
            HttpServletRequest request
    ) {
        // Check if user exists before uploading the files
        if (!userService.existsById(id)) {
            return BaseResponse.notFound("User not found");
        }
        return BaseResponse.<UserResponse>createSuccess("Successfully uploaded profile images!")
                .setPayload(userService.uploadMultipleImages(id, files, descriptions, request));
    }

    @GetMapping("/profile/download/{fileName}")
    public ResponseEntity<?> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
        return fileService.serveFile(fileName, request);
    }
}
