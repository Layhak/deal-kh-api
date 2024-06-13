package co.istad.dealkh.features.discounttype;

import co.istad.dealkh.features.discounttype.dto.DiscountTypeRequest;
import co.istad.dealkh.features.discounttype.dto.DiscountTypeResponse;

import java.util.List;

public interface DiscountTypeService {

    DiscountTypeResponse createDiscountType(DiscountTypeRequest discountTypeRequest);

    List<DiscountTypeResponse> getAllDiscountType();

    DiscountTypeResponse getDiscountTypeByName(String name);

    DiscountTypeResponse updateDiscountType(String name, DiscountTypeRequest discountTypeRequest);

    void deleteDiscountType(String name);

}
