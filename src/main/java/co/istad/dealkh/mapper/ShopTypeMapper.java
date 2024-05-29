package co.istad.dealkh.mapper;

import co.istad.dealkh.domain.ShopType;

import co.istad.dealkh.features.shoptype.dto.ShopTypeCreateRequest;
import co.istad.dealkh.features.shoptype.dto.ShopTypeRequest;
import co.istad.dealkh.features.shoptype.dto.ShopTypeResponse;
import co.istad.dealkh.features.shoptype.dto.ShopTypeUpdateRequest;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface ShopTypeMapper {
    ShopTypeResponse mapShopTypeToShopTypeResponse(ShopType shopType);

    ShopType mapShopTypeRequestToShopType(ShopTypeCreateRequest shopTypeCreateRequest);

    @Mapping(target = "id", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void mapShopTypeUpdateRequest(@MappingTarget ShopType shopType, ShopTypeUpdateRequest shopTypeUpdateRequest);

}
