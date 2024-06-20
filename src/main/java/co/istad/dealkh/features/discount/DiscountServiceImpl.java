package co.istad.dealkh.features.discount;


import co.istad.dealkh.domain.Discount;
import co.istad.dealkh.domain.DiscountType;
import co.istad.dealkh.domain.Shop;
import co.istad.dealkh.features.discount.dto.DiscountCreateRequest;
import co.istad.dealkh.features.discount.dto.DiscountResponseDetail;
import co.istad.dealkh.features.discount.dto.DiscountUpdateRequest;
import co.istad.dealkh.features.discounttype.DiscountTypeRepository;
import co.istad.dealkh.features.shop.ShopRepository;
import co.istad.dealkh.mapper.DiscountMapper;
import co.istad.dealkh.paging.PageResponse;
import co.istad.dealkh.paging.Pagination;
import co.istad.dealkh.specification.filter.DiscountFilter;
import co.istad.dealkh.specification.filter.DiscountSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * DiscountServiceImpl is a service implementation of {@link DiscountService} that handles discount-related operations.
 * It includes methods for creating, retrieving, updating, and deleting discounts.
 *
 * <p>This class uses the following annotations:
 * <ul>
 * <li>{@link Service} - Indicates that this class is a Spring service.</li>
 * <li>{@link RequiredArgsConstructor} - Generates a constructor with required arguments (final fields).</li>
 * </ul>
 * </p>
 */
@Service
@RequiredArgsConstructor
public class DiscountServiceImpl implements DiscountService {

    private final DiscountRepository discountRepository;
    private final DiscountMapper discountMapper;
    private final DiscountTypeRepository discountTypeRepository;
    private final ShopRepository shopRepository;

    /**
     * Creates a new discount based on the provided request.
     *
     * @param discountCreateRequest the request containing the details for the new discount
     * @return a {@link DiscountResponseDetail} containing the details of the created discount
     * @throws ResponseStatusException if the discount name already exists
     */
    @Override
    public DiscountResponseDetail createDiscount(DiscountCreateRequest discountCreateRequest) {

        if (discountRepository.existsByDiscountValueAndDiscountTypeSlug(discountCreateRequest.discountValue(), discountCreateRequest.discountTypeSlug())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Discount value already exists for this discount type");
        }

        DiscountType discountType = discountTypeRepository.findBySlug(discountCreateRequest.discountTypeSlug())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        String.format("Discount type with slug %s not found! ", discountCreateRequest.discountTypeSlug())));

        Shop shop = shopRepository.findBySlug(discountCreateRequest.shopSlug())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        String.format("Shop with slug %s not found! ", discountCreateRequest.shopSlug())));

        Discount newDiscount = discountMapper.mapDiscountRequestToDiscount(discountCreateRequest);

        newDiscount.setDiscountType(discountType);
        newDiscount.setShop(shop);
        newDiscount.setIsExpired(false);
        if (discountCreateRequest.expiredAt().isBefore(LocalDate.now())) {
            newDiscount.setIsExpired(true);
        }
        newDiscount.setCreatedAt(LocalDateTime.now());

        return discountMapper.mapDiscountToResponseDetail(discountRepository.save(newDiscount));
    }

    /**
     * Retrieves a discount by its uuid.
     *
     * @param uuid the uuid of the discount to retrieve
     * @return a {@link DiscountResponseDetail} containing the details of the retrieved discount
     * @throws ResponseStatusException if the discount is not found
     */
    @Override
    public Optional<DiscountResponseDetail> getDiscountByUuid(String uuid) {

        Discount discount = discountRepository.findByUuid(uuid).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Discount with uuid %s not found!", uuid)));

        DiscountResponseDetail discountResponse = discountMapper.mapDiscountToResponseDetail(discount);
        return Optional.of(discountResponse);
    }


    /**
     * Retrieves all discounts.
     *
     * @return a list of {@link DiscountResponseDetail} containing the details of all discounts
     */
    @Override
    public PageResponse<DiscountResponseDetail> getAllDiscounts(int pageNumber, int size, String field, String order, Map<String, String> params) {

        DiscountFilter discountFilter = new DiscountFilter();
        pageNumber = Pagination.page_number;
        size = Pagination.page_limit;

        if (params.containsKey("name")) {
            String name = params.get("name");
            discountFilter.setName(name);
        }

        if (params.containsKey("discountPercentage")) {
            String discountPercentage = params.get("discountPercentage");
            discountFilter.setDiscountPercentage(Double.parseDouble(discountPercentage));
        }
        List<String> validFields = Arrays.asList("uuid", "discountPercentage", "createdAt", "updatedAt");
        if (field == null || field.isEmpty() || !validFields.contains(field)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Field must be uuid, discountPercentage, or createdAt");
        }
        if (order != null && !order.equals("asc") && !order.equals("desc")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Order must be asc or desc");
        }
        DiscountSpecification specification = new DiscountSpecification(discountFilter);
        Pageable pageable = Pagination.getPageable(pageNumber, size, Sort.by(Sort.Direction.fromString(order), field));

        Page<DiscountResponseDetail> page = discountRepository.findAll(specification, pageable)
                .map(discountMapper::mapDiscountToResponseDetail);

        return new PageResponse<>(page);

    }

    /**
     * Updates a discount identified by its name based on the provided request.
     *
     * @param uuid                  the uuid of the discount to update
     * @param discountUpdateRequest the request containing the updated details for the discount
     * @return
     */
    @Override
    public DiscountResponseDetail updateDiscountByUuid(String username, String uuid, DiscountUpdateRequest discountUpdateRequest) {
        Discount discount = discountRepository.findByUuid(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Discount with uuid %s not found!", uuid)));

        if (discountRepository.findByCreatedByAndUuid(username, uuid).isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "You're not this resource owner!");
        }

        discount.setUpdatedAt(LocalDateTime.now());
        discount.setUpdatedBy(username);

        discountMapper.mapDiscountToUpdateRequest(discount, discountUpdateRequest);

        discount = discountRepository.save(discount);

        return discountMapper.mapDiscountToResponseDetail(discount);
    }

    @Override
    public void deleteDiscountByUuid(String username, String uuid) {

        Discount discount = discountRepository.findByUuid(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Discount with uuid %s not found!", uuid)));
        if (discountRepository.findByCreatedByAndUuid(username, uuid).isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "You're not this resource owner!");
        }

        discountRepository.delete(discount);
    }

}
