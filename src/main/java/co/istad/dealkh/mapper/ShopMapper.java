package co.istad.dealkh.mapper;

import co.istad.dealkh.domain.Shop;

import co.istad.dealkh.features.shop.dto.ShopRequest;
import co.istad.dealkh.features.shop.dto.ShopResponse;
import org.mapstruct.*;

@Mapper(componentModel = "spring" , uses = {UserMapper.class,ShopTypeMapper.class})
public interface ShopMapper {

    @Mapping(target = "users", source = "users")
    @Mapping(target = "shopType", source = "shopType")
    ShopResponse toShopResponse(Shop shop);

    @Mapping(target = "users",ignore = true)
    @Mapping(target = "shopType",ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "isDeleted", ignore = true)
    @Mapping(target = "isDisabled", ignore = true)
    Shop toShop(ShopRequest shopRequest);

}
