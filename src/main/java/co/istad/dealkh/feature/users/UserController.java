package co.istad.dealkh.feature.users;

import co.istad.dealkh.entity.User;
import co.istad.dealkh.feature.users.dto.UserRequest;
import co.istad.dealkh.feature.users.dto.UserResponse;
import co.istad.dealkh.paging.PageResponse;
import co.istad.dealkh.utils.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    @GetMapping()
    public BaseResponse getAllUsers(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "2") int size, @RequestParam(defaultValue = "id") String field, @RequestParam(defaultValue = "asc") String order) {
        // Validate page and size
        if (page < 0 || size <= 0) {
            return BaseResponse.error("Page and size must be greater than 0");
        }

        // Validate field
        List<String> validFields = Arrays.asList("id", "username", "email", "dob");
        if (field == null || field.isEmpty() || !validFields.contains(field)) {
            return BaseResponse.error("Field must be id, username, email, or dob");
        }

        // Validate order
        if (order != null && !order.equals("asc") && !order.equals("desc")) {
            return BaseResponse.error("Order must be asc or desc");
        }

        // Create sort object
        Sort sort = Sort.by(Sort.Direction.fromString(order), field);
        return BaseResponse.<PageResponse<UserResponse>>ok("Successfully retrieve data!!").setPayload(userService.getAllUsers(page, size, sort));
    }

    @GetMapping("/{id}")
    BaseResponse<UserResponse> getUserById(@PathVariable Long id) {
        return BaseResponse.<UserResponse>ok("Successfully retrieve data with id:" + id).setPayload(userService.getById(id));
    }

    @PostMapping
    @Operation(summary = "Create a new category", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(schema = @Schema(implementation = User.class), examples = @ExampleObject(value = """
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

}
