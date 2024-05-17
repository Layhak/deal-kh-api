package co.istad.deal_kh.dealkhapi.feature.users;

import co.istad.deal_kh.dealkhapi.feature.users.dto.UserRequest;
import co.istad.deal_kh.dealkhapi.feature.users.dto.UserResponse;

public interface UserService {
    void create(UserRequest userRequest);
    UserResponse getById(Long id);
}
