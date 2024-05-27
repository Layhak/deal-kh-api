package co.istad.dealkh.mapper;

import co.istad.dealkh.entity.ShopType;
import co.istad.dealkh.feature.shoptype.dto.ShopTypeRequest;
import co.istad.dealkh.feature.shoptype.dto.ShopTypeResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ShopTypeMapper {
    ShopTypeResponse toShopTypeResponse(ShopType shopType);
    ShopType toShopType(ShopTypeRequest shopTypeRequest);
}
