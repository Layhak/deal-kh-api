package co.istad.dealkh.features.discount.web;

import co.istad.dealkh.base.BaseResponse;
import co.istad.dealkh.features.discount.DiscountService;
import co.istad.dealkh.features.discount.dto.DiscountCreateRequest;
import co.istad.dealkh.features.discount.dto.DiscountTypeResponse;
import co.istad.dealkh.features.discount.dto.DiscountUpdateRequest;
import co.istad.dealkh.paging.PageResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/discounts")
public class DiscountController {

    private final DiscountService discountService;

    @PostMapping("")
    BaseResponse<DiscountTypeResponse> createDiscount(@RequestBody @Valid DiscountCreateRequest discountCreateRequest) {
        return BaseResponse.<DiscountTypeResponse>createSuccess("Successfully created discount!")
                .setPayload(discountService.createDiscount(discountCreateRequest));
    }

    @GetMapping("/{id}")
    BaseResponse<Optional<DiscountTypeResponse>> getDiscountById(@PathVariable Long id) {
        return BaseResponse.<Optional<DiscountTypeResponse>>ok("Successfully retrieved discount details!")
                .setPayload(discountService.getDiscountById(id));
    }

//    @GetMapping("/{name}")
//    BaseResponse<Optional<DiscountResponseDetail>> getDiscountById(@PathVariable String name) {
//        return BaseResponse.<Optional<DiscountResponseDetail>>ok("Successfully retrieved discount details!")
//                .setPayload(discountService.getDiscountByName(name));
//    }

    //    @GetMapping("/")
//    BaseResponse<List<DiscountTypeResponse>> getAllDiscount() {
//        return BaseResponse.<List<DiscountTypeResponse>>ok("Successfully retrieved discounts!")
//                .setPayload(discountService.getAllDiscounts());
//    }
//
    @GetMapping("")
    PageResponse<DiscountTypeResponse> filterDiscount(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "2") int size,
            @RequestParam(defaultValue = "id") String field,
            @RequestParam(defaultValue = "asc") String order,
            @RequestParam Map<String, String> params) {
        return discountService.getAllDiscounts(page, size, field, order, params);
    }

    @PutMapping("/{id}")
    BaseResponse<DiscountTypeResponse> updateDiscountById(@PathVariable Long id, @RequestBody DiscountUpdateRequest discountUpdateRequest) {
        return BaseResponse.<DiscountTypeResponse>ok("Update discount successfully!")
                .setPayload(discountService.updateDiscountById(id, discountUpdateRequest));
    }

    @DeleteMapping("/{id}")
    BaseResponse<?> deleteDiscountById(@PathVariable Long id) {
        discountService.deleteDiscountById(id);
        return BaseResponse.ok("Delete discount successfully!");
    }

}
