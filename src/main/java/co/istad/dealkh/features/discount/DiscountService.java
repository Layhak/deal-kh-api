package co.istad.dealkh.features.discount;

import co.istad.dealkh.features.discount.dto.DiscountCreateRequest;
import co.istad.dealkh.features.discount.dto.DiscountTypeResponse;
import co.istad.dealkh.features.discount.dto.DiscountUpdateRequest;
import co.istad.dealkh.paging.PageResponse;

import java.util.Map;
import java.util.Optional;

public interface DiscountService {

    DiscountTypeResponse createDiscount(DiscountCreateRequest discountCreateRequest);

    Optional<DiscountTypeResponse> getDiscountById(Long id);

    Optional<DiscountTypeResponse> getDiscountByName(String name);


    PageResponse<DiscountTypeResponse> getAllDiscounts(int page, int size, String field, String order, Map<String, String> params);

    DiscountTypeResponse updateDiscountById(Long id, DiscountUpdateRequest discountUpdateRequest);

    void deleteDiscountById(Long id);
}
