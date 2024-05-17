package co.istad.deal_kh.dealkhapi.mapper;
import co.istad.deal_kh.dealkhapi.domain.Role;
import co.istad.deal_kh.dealkhapi.domain.Shop;
import co.istad.deal_kh.dealkhapi.domain.SocialMedia;
import co.istad.deal_kh.dealkhapi.domain.User;
import co.istad.deal_kh.dealkhapi.feature.users.dto.UserRequest;
import co.istad.deal_kh.dealkhapi.feature.users.dto.UserResponse;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(source = "role", target = "role", qualifiedByName = "roleToString")
    @Mapping(source = "shops", target = "shops", qualifiedByName = "shopsToListOfStrings")
    @org.mapstruct.Mapping(target = "socialMedias", expression = "java(mapSocialMedias(user.getSocialMedias()))")
    UserResponse mapToUserResponse(User user);

    @Named("roleToString")
    default String roleToString(Role role) {
        return role.getName();
    }

    default List<String> mapSocialMedias(List<SocialMedia> socialMedias) {
        return socialMedias.stream().map(SocialMedia::getSocialName).collect(Collectors.toList());
    }

    @Named("shopsToListOfStrings")
    default List<String> shopsToListOfStrings(List<Shop> shops) {
        if (shops == null) {
            return Collections.emptyList();
        }
        return shops.stream().map(Shop::getName).collect(Collectors.toList());
    }

}
