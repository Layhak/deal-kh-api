package co.istad.dealkh.mapper;

import co.istad.dealkh.domain.Discount;
import co.istad.dealkh.features.discount.dto.DiscountCreateRequest;
import co.istad.dealkh.features.discount.dto.DiscountResponseDetail;
import co.istad.dealkh.features.discount.dto.DiscountUpdateRequest;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface DiscountMapper {

    @Mapping(source = "discountType.name", target = "discountType")
    DiscountResponseDetail mapDiscountToResponseDetail(Discount discount);

    Discount mapDiscountRequestToDiscount(DiscountCreateRequest discountCreateRequest);

    @Mapping(target = "id", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void mapDiscountToUpdateRequest(@MappingTarget Discount discount, DiscountUpdateRequest discountUpdateRequest);

}
