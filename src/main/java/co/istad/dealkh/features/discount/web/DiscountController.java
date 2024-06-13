package co.istad.dealkh.features.discount.web;

import co.istad.dealkh.base.BaseResponse;
import co.istad.dealkh.features.discount.DiscountService;
import co.istad.dealkh.features.discount.dto.DiscountCreateRequest;
import co.istad.dealkh.features.discount.dto.DiscountResponseDetail;
import co.istad.dealkh.features.discount.dto.DiscountUpdateRequest;
import co.istad.dealkh.paging.PageResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

/**
 * DiscountController is a controller for managing discounts.
 * It handles creating, retrieving, updating, and deleting discounts.
 *
 * <p>This class uses the following annotations:
 * <ul>
 * <li>{@link RestController} - Indicates that this class is a REST controller.</li>
 * <li>{@link RequiredArgsConstructor} - Generates a constructor with required arguments (final fields).</li>
 * <li>{@link RequestMapping} - Maps HTTP requests to handler methods of MVC and REST controllers.</li>
 * </ul>
 * </p>
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/discounts")
public class DiscountController {

    private final DiscountService discountService;

    /**
     * Creates a new discount based on the provided request.
     *
     * @param discountCreateRequest the request containing the details for the new discount
     * @return a {@link DiscountResponseDetail} containing the details of the created discount
     */
    @PostMapping("")
    BaseResponse<DiscountResponseDetail> createDiscount(@RequestBody @Valid DiscountCreateRequest discountCreateRequest) {
        return BaseResponse.<DiscountResponseDetail>createSuccess("Created new discount!")
                .setPayload(discountService.createDiscount(discountCreateRequest));
    }

    /**
     * Retrieves a discount by its ID.
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    BaseResponse<Optional<DiscountResponseDetail>> getDiscountById(@PathVariable Long id) {
        return BaseResponse.<Optional<DiscountResponseDetail>>ok("Retrieved discount with id " + id + " successfully!")
                .setPayload(discountService.getDiscountById(id));
    }

//    @GetMapping("/{name}")
//    BaseResponse<Optional<DiscountResponseDetail>> getDiscountById(@PathVariable String name) {
//        return BaseResponse.<Optional<DiscountResponseDetail>>ok("Successfully retrieved discount details!")
//                .setPayload(discountService.getDiscountByName(name));
//    }

    //    @GetMapping("/")
//    BaseResponse<List<DiscountResponseDetail>> getAllDiscount() {
//        return BaseResponse.<List<DiscountResponseDetail>>ok("Successfully retrieved discounts!")
//                .setPayload(discountService.getAllDiscounts());
//    }
//

    /**
     * Retrieves all discounts.
     *
     * @param page
     * @param size
     * @param field
     * @param order
     * @param params
     * @return
     */
    @GetMapping()
    BaseResponse<PageResponse<DiscountResponseDetail>> filterDiscount(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "2") int size,
            @RequestParam(defaultValue = "id") String field,
            @RequestParam(defaultValue = "asc") String order,
            @RequestParam Map<String, String> params) {
        return BaseResponse.<PageResponse<DiscountResponseDetail>>ok("Retrieves all discounts successfully!")
                .setPayload(discountService.getAllDiscounts(page, size, field, order, params));
    }

    /**
     * Updates a discount identified by its name based on the provided request.
     *
     * @param id                    the ID of the discount to update
     * @param discountUpdateRequest the request containing the updated details for the discount
     * @return a {@link DiscountResponseDetail} containing the details of the updated discount
     */
    @PutMapping("/{id}")
    BaseResponse<DiscountResponseDetail> updateDiscountById(@PathVariable Long id, @RequestBody DiscountUpdateRequest discountUpdateRequest) {
        return BaseResponse.<DiscountResponseDetail>ok("Discount has been updated!")
                .setPayload(discountService.updateDiscountById(id, discountUpdateRequest));
    }

    /**
     * Deletes a discount identified by its name.
     *
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    BaseResponse<?> deleteDiscountById(@PathVariable Long id) {
        discountService.deleteDiscountById(id);
        return BaseResponse.ok("Discount has been deleted!")
                .setPayload("No content");
    }

}
