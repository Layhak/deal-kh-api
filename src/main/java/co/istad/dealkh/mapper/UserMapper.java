package co.istad.dealkh.mapper;

import co.istad.dealkh.domain.Role;
import co.istad.dealkh.domain.User;
import co.istad.dealkh.features.user.dto.UserRequest;
import co.istad.dealkh.features.user.dto.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = {CustomMapper.class})
public interface UserMapper {
    @Mapping(source = "roles", target = "roles", qualifiedByName = "roleToString")
    @Mapping(source = "images", target = "images")
    UserResponse mapToUserResponse(User user);

    //    @Mapping(source = "shops", target = "shops")
//    @Mapping(source = "roles", target = "roles", qualifiedByName = "stringToRole")
    @Mapping(target = "roles", ignore = true)
    User mapRequestToUser(UserRequest userRequest);



//    @Named("stringToRole")
//    default Set<Role> mapStringToRole(Set<String> roles){
//        return roles.stream()
//                .map(roleName -> // Replace with your logic to convert name to Role object
//                        new Role(roleName)) // Example: create a new Role with the name
//                .collect(Collectors.toSet());
//    }

    @Named("roleToString")
    default Set<String> mapRoleToString(Set<Role> roles){
        return roles.stream().map(Role::getName).collect(Collectors.toSet());
    }
}
