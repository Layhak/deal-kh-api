package co.istad.dealkh.features.discount;

import co.istad.dealkh.features.discount.dto.DiscountCreateRequest;
import co.istad.dealkh.features.discount.dto.DiscountResponseDetail;
import co.istad.dealkh.features.discount.dto.DiscountUpdateRequest;
import co.istad.dealkh.paging.PageResponse;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface DiscountService {

    DiscountResponseDetail createDiscount(DiscountCreateRequest discountCreateRequest);

    Optional<DiscountResponseDetail> getDiscountById(Long id);

    Optional<DiscountResponseDetail> getDiscountByName(String name);

    List<DiscountResponseDetail> getAllDiscounts();

    PageResponse<DiscountResponseDetail> filterDiscount(Map<String, String> params);

    DiscountResponseDetail updateDiscountById(Long id, DiscountUpdateRequest discountUpdateRequest);

    void deleteDiscountById(Long id);
}
