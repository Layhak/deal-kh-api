package co.istad.deal_kh.dealkhapi.feature.users;

import co.istad.deal_kh.dealkhapi.feature.users.dto.UserResponse;
import co.istad.deal_kh.dealkhapi.utils.BaseResponse;
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
        return BaseResponse.<UserResponse>ok()
                .setPayload(userService.getById(id));
    }
}
