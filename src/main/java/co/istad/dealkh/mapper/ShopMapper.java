package co.istad.dealkh.mapper;

import co.istad.dealkh.domain.Shop;
import co.istad.dealkh.features.shop.dto.ShopCreateRequest;
import co.istad.dealkh.features.shop.dto.ShopResponse;
import co.istad.dealkh.features.shop.dto.ShopUpdateRequest;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = {CustomMapper.class})
public interface ShopMapper {

    @Mapping(target = "owners", source = "users", qualifiedByName = "userToString")
    @Mapping(target = "shopType", source = "shopType", qualifiedByName = "shopTypeToString")
    @Mapping(target = "slug", source = "slug")
    ShopResponse toShopResponse(Shop shop);

    @Mapping(source = "openAt", target = "openAt", qualifiedByName = "stringToLocalTime")
    @Mapping(source = "closeAt", target = "closeAt", qualifiedByName = "stringToLocalTime")
    @Mapping(source = "shopType", target = "shopType", qualifiedByName = "stringToShopType")
    Shop toShop(ShopCreateRequest shopRequest);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "slug", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void mapUpdateShopToShop(@MappingTarget Shop shop, ShopUpdateRequest shopUpdateRequest);
}