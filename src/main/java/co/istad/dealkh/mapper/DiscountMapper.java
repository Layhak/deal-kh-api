package co.istad.dealkh.mapper;

import co.istad.dealkh.domain.Discount;
import co.istad.dealkh.features.discount.dto.DiscountCreateRequest;
import co.istad.dealkh.features.discount.dto.DiscountTypeResponse;
import co.istad.dealkh.features.discount.dto.DiscountUpdateRequest;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = CustomMapper.class)
public interface DiscountMapper {

    @Mapping(source = "discountType.name", target = "discountType")
    DiscountTypeResponse mapDiscountToResponseDetail(Discount discount);

    @Mapping(target = "discountType", source = "discountTypeId", qualifiedByName = "discountTypeToLong")
    Discount mapDiscountRequestToDiscount(DiscountCreateRequest discountCreateRequest);


    @Mapping(target = "id", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void mapDiscountToUpdateRequest(@MappingTarget Discount discount, DiscountUpdateRequest discountUpdateRequest);

}
