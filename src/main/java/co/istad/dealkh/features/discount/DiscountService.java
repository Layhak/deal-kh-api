package co.istad.dealkh.features.discount;

import co.istad.dealkh.features.discount.dto.DiscountCreateRequest;
import co.istad.dealkh.features.discount.dto.DiscountResponseDetail;
import co.istad.dealkh.features.discount.dto.DiscountUpdateRequest;
import co.istad.dealkh.paging.PageResponse;

import java.util.Map;
import java.util.Optional;

/**
 * DiscountService is a service for managing discounts.
 * It includes methods for creating, retrieving, updating, and deleting discounts.
 */
public interface DiscountService {

    /**
     * Creates a new discount based on the provided request.
     *
     * @param discountCreateRequest the request containing the details for the new discount
     * @return a {@link DiscountResponseDetail} containing the details of the created discount
     */
    DiscountResponseDetail createDiscount(DiscountCreateRequest discountCreateRequest);

    /**
     * Retrieves a discount by its ID.
     *
     * @param id the ID of the discount to retrieve
     * @return a {@link DiscountResponseDetail} containing the details of the retrieved discount
     */
    Optional<DiscountResponseDetail> getDiscountById(Long id);

    /**
     * Retrieves a discount by its name.
     *
     * @param name the name of the discount to retrieve
     * @return an {@link Optional} containing the {@link DiscountResponseDetail} if found, or empty if not found
     */
    Optional<DiscountResponseDetail> getDiscountByName(String name);

    /**
     * Retrieves all discounts.
     *
     * @return a list of {@link DiscountResponseDetail} containing the details of all discounts
     */
    PageResponse<DiscountResponseDetail> getAllDiscounts(int page, int size, String field, String order, Map<String, String> params);

    /**
     * Updates a discount identified by its name based on the provided request.
     *
     * @param id                    the ID of the discount to update
     * @param discountUpdateRequest the request containing the updated details for the discount
     * @return a {@link DiscountResponseDetail} containing the details of the updated discount
     */
    DiscountResponseDetail updateDiscountById(String username, Long id, DiscountUpdateRequest discountUpdateRequest);

    /**
     * Deletes a discount identified by its name.
     *
     * @param id
     */
    void deleteDiscountById(String username, Long id);
}
