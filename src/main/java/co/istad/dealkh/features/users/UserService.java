package co.istad.dealkh.features.users;

import co.istad.dealkh.features.users.dto.UserProfileResponse;
import co.istad.dealkh.features.users.dto.UserRequest;
import co.istad.dealkh.features.users.dto.UserResponse;
import co.istad.dealkh.paging.PageResponse;
import org.springframework.data.domain.Sort;

public interface UserService {

    UserResponse getById(Long id);

    PageResponse getAllUsers(int page, int size, Sort sort);

    UserProfileResponse getUserProfile(Long id);

    UserResponse createUser(UserRequest userRequest);

    UserResponse updateUser(UserRequest userRequest);

    void deleteUser(Long id);
}
