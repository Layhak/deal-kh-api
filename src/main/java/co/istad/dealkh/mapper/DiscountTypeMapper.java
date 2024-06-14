package co.istad.dealkh.mapper;

import co.istad.dealkh.domain.Discount;
import co.istad.dealkh.domain.DiscountType;
import co.istad.dealkh.features.discount.dto.DiscountUpdateRequest;
import co.istad.dealkh.features.discounttype.dto.DiscountTypeRequest;
import co.istad.dealkh.features.discounttype.dto.DiscountTypeResponse;
import co.istad.dealkh.features.discounttype.dto.DiscountTypeUpdateRequest;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DiscountTypeMapper {

    DiscountTypeResponse mapToDiscountTypeResponse(DiscountType discountType);

    DiscountType mapRequestToDiscountType(DiscountTypeRequest discountTypeRequest);

    @Mapping(target = "id", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void mapDiscountToUpdateRequest(@MappingTarget DiscountType discountType, DiscountTypeUpdateRequest discountTypeUpdateRequest);



}
