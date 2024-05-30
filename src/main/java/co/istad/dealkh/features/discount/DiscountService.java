package co.istad.dealkh.features.discount;

import co.istad.dealkh.features.discount.dto.DiscountCreateRequest;
import co.istad.dealkh.features.discount.dto.DiscountResponse;
import co.istad.dealkh.features.discount.dto.DiscountUpdateRequest;
import co.istad.dealkh.paging.PageResponse;

import java.util.Map;
import java.util.Optional;

public interface DiscountService {

    DiscountResponse createDiscount(DiscountCreateRequest discountCreateRequest);

    Optional<DiscountResponse> getDiscountById(Long id);

    Optional<DiscountResponse> getDiscountByName(String name);


    PageResponse<DiscountResponse> getAllDiscounts(int page, int size, String field, String order, Map<String, String> params);

    DiscountResponse updateDiscountById(Long id, DiscountUpdateRequest discountUpdateRequest);

    void deleteDiscountById(Long id);
}
