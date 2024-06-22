package co.istad.dealkh.mapper;

import co.istad.dealkh.domain.Discount;
import co.istad.dealkh.domain.Shop;
import co.istad.dealkh.features.discount.dto.DiscountCreateRequest;
import co.istad.dealkh.features.discount.dto.DiscountResponseDetail;
import co.istad.dealkh.features.discount.dto.DiscountUpdateRequest;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = CustomMapper.class)
public interface DiscountMapper {

    @Mapping(source = "discountType.name", target = "discountType")
    @Mapping(target = "shop", source = "shop", qualifiedByName = "shopToString")
    DiscountResponseDetail mapDiscountToResponseDetail(Discount discount);

    @Mapping(target = "discountType", source = "discountTypeSlug", qualifiedByName = "discountTypeToString")
    Discount mapDiscountRequestToDiscount(DiscountCreateRequest discountCreateRequest);


    @Mapping(target = "id", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void mapDiscountToUpdateRequest(@MappingTarget Discount discount, DiscountUpdateRequest discountUpdateRequest);

    @Named("shopToString")
    default String mapShop(Shop shop) {
        return shop.getName();
    }

}
