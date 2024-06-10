package co.istad.dealkh.features.discount;


import co.istad.dealkh.domain.Discount;
import co.istad.dealkh.features.discount.dto.DiscountCreateRequest;
import co.istad.dealkh.features.discount.dto.DiscountTypeResponse;
import co.istad.dealkh.features.discount.dto.DiscountUpdateRequest;
import co.istad.dealkh.features.discounttype.DiscountTypeRepository;
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

    /**
     * Creates a new discount based on the provided request.
     *
     * @param discountCreateRequest the request containing the details for the new discount
     * @return a {@link DiscountTypeResponse} containing the details of the created discount
     * @throws ResponseStatusException if the discount name already exists
     */
    @Override
    public DiscountTypeResponse createDiscount(DiscountCreateRequest discountCreateRequest) {

        if (discountRepository.existsByDiscountPercentageAndDiscountTypeId(discountCreateRequest.discountPercentage(), discountCreateRequest.discountTypeId())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Discount percentage already exists for this discount type");
        }

        Discount newDiscount = discountMapper.mapDiscountRequestToDiscount(discountCreateRequest);
        return discountMapper.mapDiscountToResponseDetail(discountRepository.save(newDiscount));
    }

    /**
     * Retrieves a discount by its ID.
     *
     * @param id the ID of the discount to retrieve
     * @return a {@link DiscountTypeResponse} containing the details of the retrieved discount
     * @throws ResponseStatusException if the discount is not found
     */
    @Override
    public Optional<DiscountTypeResponse> getDiscountById(Long id) {

        Discount discount = discountRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Discount id not found!"));

        DiscountTypeResponse discountResponse = discountMapper.mapDiscountToResponseDetail(discount);
        return Optional.of(discountResponse);
    }

    /**
     * Retrieves a discount by its name.
     *
     * @param name the name of the discount to retrieve
     * @return
     */
    @Override
    public Optional<DiscountTypeResponse> getDiscountByName(String name) {

//        Discount discount = discountRepository.findByName(name)
//                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Discount name not found!"));
//
//        DiscountResponseDetail discountResponseDetail = discountMapper.mapDiscountToResponseDetail(discount);
//        return Optional.of(discountResponseDetail);
        return null;
    }

    /**
     * Retrieves all discounts.
     *
     * @return a list of {@link DiscountTypeResponse} containing the details of all discounts
     */
    @Override
    public PageResponse<DiscountTypeResponse> getAllDiscounts(int pageNumber, int size, String field, String order, Map<String, String> params) {

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
        List<String> validFields = Arrays.asList("id", "discountPercentage", "createdAt", "updatedAt");
        if (field == null || field.isEmpty() || !validFields.contains(field)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Field must be id, discountPercentage, or createdAt");
        }
        if (order != null && !order.equals("asc") && !order.equals("desc")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Order must be asc or desc");
        }
        DiscountSpecification specification = new DiscountSpecification(discountFilter);
        Pageable pageable = Pagination.getPageable(pageNumber, size, Sort.by(Sort.Direction.fromString(order), field));

        Page<DiscountTypeResponse> page = discountRepository.findAll(specification, pageable)
                .map(discountMapper::mapDiscountToResponseDetail);

        return new PageResponse<>(page);

    }

    /**
     * Updates a discount identified by its name based on the provided request.
     *
     * @param id                    the ID of the discount to update
     * @param discountUpdateRequest the request containing the updated details for the discount
     * @return
     */
    @Override
    public DiscountTypeResponse updateDiscountById(Long id, DiscountUpdateRequest discountUpdateRequest) {
        Discount discount = discountRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Discount id not found!"));

        discount.setUpdatedAt(LocalDateTime.now());

        discountMapper.mapDiscountToUpdateRequest(discount, discountUpdateRequest);

        discount = discountRepository.save(discount);

        return discountMapper.mapDiscountToResponseDetail(discount);
    }

    /**
     * Deletes a discount identified by its name.
     *
     * @param id
     */
    @Override
    public void deleteDiscountById(Long id) {
        Discount discount = discountRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Discount id not found!"));

        discountRepository.delete(discount);
    }
}
