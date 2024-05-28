package co.istad.dealkh.features.users;

import co.istad.dealkh.entity.User;
import co.istad.dealkh.features.files.FileService;
import co.istad.dealkh.features.files.dto.FileResponse;
import co.istad.dealkh.features.users.dto.UserRequest;
import co.istad.dealkh.features.users.dto.UserResponse;
import co.istad.dealkh.paging.PageResponse;
import co.istad.dealkh.utils.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;
    private final FileService fileService;

    @GetMapping()
    public BaseResponse getAllUsers(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "2") int size, @RequestParam(defaultValue = "username") String field, @RequestParam(defaultValue = "asc") String order) {
        return BaseResponse.<PageResponse<UserResponse>>ok("Successfully retrieve data!!").setPayload(userService.getAllUsers(page, size, field, order));
    }

    @GetMapping("/{id}")
    BaseResponse<UserResponse> getUserById(@PathVariable Long id) {
        return BaseResponse.<UserResponse>ok("Successfully retrieve data with id:" + id).setPayload(userService.getById(id));
    }

    @PostMapping
    @Operation(summary = "Create a new user", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(schema = @Schema(implementation = User.class), examples = @ExampleObject(value = """
            {
              "firstName": "Art",
              "lastName": "Vanndeth",
              "username": "Vanndeth",
              "email": "vanndeth@gmail.com",
              "password": "123456",
              "gender": "Male",
              "images": [
                {
                  "url": "http://localhost:8080/api/v1/users/1/images/1",
                  "description": "Profile Picture"
                }
              ],
              "phoneNumber": "0123456789",
              "dob": "1999-01-01",
              "location": "Phnom Penh",
              "socialMedias": [
                {
                  "socialName": "Facebook",
                  "socialLink": "www.facebook.com",
                  "socialIcon": "facebook_icon.jpg"
                },
                {
                  "socialName": "Telegram",
                  "socialLink": "www.telegram.com",
                  "socialIcon": "telegram_icon.jpg"
                }
              ],
              "role": "SUPER_ADMIN"
            }
                        """))))
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


    @PostMapping(value = "/{id}/profile/upload", consumes = "multipart/form-data")
    @Operation(summary = "Upload profile picture and update user")
    @ResponseStatus(HttpStatus.CREATED)
    public BaseResponse<UserResponse> uploadProfileImage(
            @PathVariable Long id,
            @RequestPart("file") MultipartFile file,
            @RequestParam("description") String description,
            HttpServletRequest request
    ) {
        // Check if user exists before uploading the file
        if (!userService.existsById(id)) {
            return BaseResponse.<UserResponse>notFound("User not found");
        }

        // Upload the file
        FileResponse fileResponse = fileService.uploadSingleFile(file, request);

        // Update user image information
        UserResponse userResponse = userService.updateUserImage(id, fileResponse.fullUrl(), description);
        return BaseResponse.<UserResponse>createSuccess("Successfully uploaded profile image and updated user!")
                .setPayload(userResponse);
    }

    @GetMapping("/profile/download/{fileName}")
    public ResponseEntity<?> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
        return fileService.serveFile(fileName, request);
    }

}
