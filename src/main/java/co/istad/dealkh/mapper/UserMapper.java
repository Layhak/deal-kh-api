package co.istad.dealkh.mapper;

import co.istad.dealkh.domain.User;
import co.istad.dealkh.feature.users.dto.UserRequest;
import co.istad.dealkh.feature.users.dto.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {RoleMapper.class})
public interface UserMapper {
    @Mapping(source = "role.name", target = "role")
    UserResponse mapToUserResponse(User user);

    //    @Mapping(source = "shops", target = "shops")
    @Mapping(source = "role", target = "role", qualifiedByName = "stringToRole")
    User mapRequestToUser(UserRequest userRequest);
}
