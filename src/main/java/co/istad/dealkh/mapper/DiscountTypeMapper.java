package co.istad.dealkh.mapper;

import co.istad.dealkh.domain.DiscountType;
import co.istad.dealkh.features.discounttype.dto.DiscountTypeRequest;
import co.istad.dealkh.features.discounttype.dto.DiscountTypeResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DiscountTypeMapper {

    DiscountTypeResponse mapToDiscountTypeResponse(DiscountType discountType);

    DiscountType mapRequestToDiscountType(DiscountTypeRequest discountTypeRequest);
}
