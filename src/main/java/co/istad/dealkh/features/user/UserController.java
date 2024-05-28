package co.istad.dealkh.features.user;

import co.istad.dealkh.base.BaseResponse;
import co.istad.dealkh.features.user.dto.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;


    @GetMapping("/{id}")
    BaseResponse<UserResponse> getUserById(@PathVariable Long id) {
        return BaseResponse.<UserResponse>ok("Successfully retrieve data with id:" + id).setPayload(userService.getById(id));
    }

}
