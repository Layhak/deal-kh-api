package co.istad.dealkh.mapper;

import co.istad.dealkh.domain.Role;
import co.istad.dealkh.domain.User;
import co.istad.dealkh.features.user.dto.*;
import org.mapstruct.*;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = {CustomMapper.class})
public interface UserMapper {

    @Mapping(source = "roles", target = "roles", qualifiedByName = "roleToString")
    @Mapping(source = "covers", target = "covers")
    UserResponse mapToUserResponse(User user);

    @Mapping(target = "roles", ignore = true)
    User mapRequestToUser(UserRequest userRequest);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "roles", ignore = true)
    User mapCreateRequestToUser(UserCreateRequest userCreateRequest);

    @Mapping(target = "id", ignore = true)
//    @Mapping(target = "shops", source = "shopSlug", qualifiedByName = "mapShopSlugsToShops")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void mapUpdateRequestToUser(@MappingTarget User user, UserUpdateRequest userUpdateRequest);

    @Named("roleToString")
    default Set<String> mapRoleToString(Set<Role> roles) {
        return roles.stream().map(Role::getName).collect(Collectors.toSet());
    }

    @Mapping(target = "imageUrl", source = "covers")
    UserProfileResponse mapToUserProfileResponse(User user);


}
