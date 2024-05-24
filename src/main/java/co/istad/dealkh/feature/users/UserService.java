package co.istad.dealkh.feature.users;

import co.istad.dealkh.feature.users.dto.UserProfileResponse;
import co.istad.dealkh.feature.users.dto.UserRequest;
import co.istad.dealkh.feature.users.dto.UserResponse;
import co.istad.dealkh.paging.PageResponse;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

public interface UserService {

    UserResponse getById(Long id);

    PageResponse getAllUsers(int page, int size, Sort sort);

    UserProfileResponse getUserProfile(Long id);

    UserResponse createUser(UserRequest userRequest);

    UserResponse updateUser( Long id,UserRequest userRequest);

    void deleteUser(Long id);
    UserResponse disableUser(Long id);

    UserResponse enableUser(Long id);

    //getAllEnabledUsers
    List<UserResponse> getAllUsersByStatus(boolean status);

}
