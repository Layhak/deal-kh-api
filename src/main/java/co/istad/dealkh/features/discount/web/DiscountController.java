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

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/discounts")
public class DiscountController {

    private final DiscountService discountService;

    @PostMapping("")
    BaseResponse<DiscountResponseDetail> createDiscount(@RequestBody @Valid DiscountCreateRequest discountCreateRequest) {
        return BaseResponse.<DiscountResponseDetail>createSuccess("Successfully created discount!")
                .setPayload(discountService.createDiscount(discountCreateRequest));
    }

    //    @GetMapping("/{id}")
    BaseResponse<Optional<DiscountResponseDetail>> getDiscountById(@PathVariable Long id) {
        return BaseResponse.<Optional<DiscountResponseDetail>>ok("Successfully retrieved discount details!")
                .setPayload(discountService.getDiscountById(id));
    }

    @GetMapping("/{name}")
    BaseResponse<Optional<DiscountResponseDetail>> getDiscountById(@PathVariable String name) {
        return BaseResponse.<Optional<DiscountResponseDetail>>ok("Successfully retrieved discount details!")
                .setPayload(discountService.getDiscountByName(name));
    }

    //    @GetMapping("/")
    BaseResponse<List<DiscountResponseDetail>> getAllDiscount() {
        return BaseResponse.<List<DiscountResponseDetail>>ok("Successfully retrieved discounts!")
                .setPayload(discountService.getAllDiscounts());
    }

    @GetMapping("")
    PageResponse<DiscountResponseDetail> filterDiscount(@RequestParam Map<String, String> params) {
        return discountService.filterDiscount(params);
    }

    @PutMapping("/{id}")
    BaseResponse<DiscountResponseDetail> updateDiscountById(@PathVariable Long id, @RequestBody DiscountUpdateRequest discountUpdateRequest) {
        return BaseResponse.<DiscountResponseDetail>updateSuccess("Update discount successfully!")
                .setPayload(discountService.updateDiscountById(id, discountUpdateRequest));
    }

    @DeleteMapping("/{id}")
    BaseResponse<?> deleteDiscountById(@PathVariable Long id) {
        discountService.deleteDiscountById(id);
        return BaseResponse.deleteSuccess("Delete discount successfully!");
    }

}
