package co.istad.dealkh.mapper;

import co.istad.dealkh.entity.User;
import co.istad.dealkh.features.users.dto.UserRequest;
import co.istad.dealkh.features.users.dto.UserResponse;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = {RoleMapper.class})
public interface UserMapper {
    @Mapping(source = "role.name", target = "role")
    @Mapping(source = "image", target = "images")
    UserResponse mapToUserResponse(User user);

    //    @Mapping(source = "shops", target = "shops")
    @Mapping(source = "role", target = "role", qualifiedByName = "stringToRole")
    @Mapping(source = "images", target = "image")
    User mapRequestToUser(UserRequest userRequest);


}
