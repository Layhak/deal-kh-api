package co.istad.dealkh.mapper;

import co.istad.dealkh.domain.Shop;
import co.istad.dealkh.domain.User;
import co.istad.dealkh.domain.enumType.GrantStatus;
import co.istad.dealkh.domain.enumType.ShopVerify;
import co.istad.dealkh.features.shop.dto.*;
import co.istad.dealkh.features.user.dto.SellerResponse;
import co.istad.dealkh.features.user.dto.UserCoverResponse;
import co.istad.dealkh.features.user.dto.UserProfileResponse;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = {CustomMapper.class})
public interface ShopMapper {

    @Mapping(target = "owners", source = "users", qualifiedByName = "userToString")
    @Mapping(target = "shopType", source = "shopType", qualifiedByName = "shopTypeToString")
    @Mapping(target = "slug", source = "slug")
    @Mapping(target = "profile", source = "profile")
    @Mapping(target = "isVerified", source = "isVerified", qualifiedByName = "mapIsVerifiedToString")
    ShopResponse toShopResponse(Shop shop);

    @Mapping(source = "openAt", target = "openAt", qualifiedByName = "stringToLocalTime")
    @Mapping(source = "closeAt", target = "closeAt", qualifiedByName = "stringToLocalTime")
    @Mapping(source = "shopType", target = "shopType", qualifiedByName = "stringToShopTypeSlug")
    @Mapping(source = "profile", target = "profile")
    Shop toShop(ShopCreateRequest shopRequest);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "slug", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void mapUpdateShopToShop(@MappingTarget Shop shop, ShopUpdateRequest shopUpdateRequest);

    @Mapping(target = "covers", source = "covers")
    ShopCoverResponse mapToShopCoverResponse(Shop shop);


    @Mapping(target = "profile", source = "profile")
    ShopProfileResponse mapToShopProfileResponse(Shop shop);

    @Named("mapIsVerifiedToString")
    static String mapIsVerifiedToString(ShopVerify shopVerify) {
        return shopVerify == null ? null : shopVerify.name();
    }
}