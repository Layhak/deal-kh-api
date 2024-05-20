package co.istad.dealkh.feature.users;

import co.istad.dealkh.feature.users.dto.UserRequest;
import co.istad.dealkh.feature.users.dto.UserResponse;
import co.istad.dealkh.paging.CustomPage;
import co.istad.dealkh.paging.OffsetBasedPageRequest;
import co.istad.dealkh.utils.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    @GetMapping()
    BaseResponse<CustomPage<UserResponse>> getAllUsers(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "2") int size, @RequestParam(defaultValue = "id,asc") String[] sort) {

        if (page < 1) {
            page = 1;
        }

        Sort.Direction direction = Sort.Direction.fromString(sort[1]);
        Sort sortOrder = Sort.by(direction, sort[0]);
        OffsetBasedPageRequest pageable = new OffsetBasedPageRequest(page, size, sortOrder);

        org.springframework.data.domain.Page<UserResponse> usersPage = userService.getAllUsers(pageable);

        return BaseResponse.<CustomPage<UserResponse>>ok().setPayload(new CustomPage<>(usersPage));
    }

    @GetMapping("/{id}")
    BaseResponse<UserResponse> getUserById(@PathVariable Long id) {
        return BaseResponse.<UserResponse>ok().setPayload(userService.getById(id));
    }

    @PostMapping
    BaseResponse<UserResponse> createUser(@RequestBody UserRequest userRequest) {
        return BaseResponse.<UserResponse>ok().setPayload(userService.createUser(userRequest));
    }

}
