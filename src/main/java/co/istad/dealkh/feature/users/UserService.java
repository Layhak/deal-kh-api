package co.istad.dealkh.feature.users;

import co.istad.dealkh.feature.users.dto.UserProfileResponse;
import co.istad.dealkh.feature.users.dto.UserRequest;
import co.istad.dealkh.feature.users.dto.UserResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {

    UserResponse getById(Long id);

    Page<UserResponse> getAllUsers(Pageable pageable);

    UserProfileResponse getUserProfile(Long id);

    UserResponse createUser(UserRequest userRequest);

    UserResponse updateUser(UserRequest userRequest);

    void deleteUser(Long id);
}
