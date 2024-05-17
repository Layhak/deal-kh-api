package co.istad.deal_kh.dealkhapi.feature.users;

import co.istad.deal_kh.dealkhapi.domain.User;
import co.istad.deal_kh.dealkhapi.feature.users.dto.UserRequest;
import co.istad.deal_kh.dealkhapi.feature.users.dto.UserResponse;
import co.istad.deal_kh.dealkhapi.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public void create(UserRequest userRequest) {

    }

    @Override
    public UserResponse getById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User has not been found!"));
        return  userMapper.mapToUserResponse(user);
    }
}
