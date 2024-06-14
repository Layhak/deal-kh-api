package co.istad.dealkh.features.discounttype;

import co.istad.dealkh.features.discounttype.dto.DiscountTypeRequest;
import co.istad.dealkh.features.discounttype.dto.DiscountTypeResponse;
import co.istad.dealkh.features.discounttype.dto.DiscountTypeUpdateRequest;

import java.util.List;

public interface DiscountTypeService {

    DiscountTypeResponse createDiscountType(DiscountTypeRequest discountTypeRequest);

    List<DiscountTypeResponse> getAllDiscountType();

    DiscountTypeResponse getDiscountTypeByName(String name);

    DiscountTypeResponse updateDiscountType(String name, DiscountTypeUpdateRequest discountTypeUpdateRequest);

    void deleteDiscountType(String name);

}
