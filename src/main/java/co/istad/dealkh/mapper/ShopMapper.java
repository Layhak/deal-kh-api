package co.istad.dealkh.mapper;

import co.istad.dealkh.domain.Shop;
import co.istad.dealkh.features.shop.dto.ShopRequest;
import co.istad.dealkh.features.shop.dto.ShopResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "Spring")

public interface ShopMapper {

    ShopResponse toShopResponse(Shop shop);
    Shop toShop(ShopRequest shopRequest);

}
