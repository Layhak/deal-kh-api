package co.istad.dealkh.features.discounttype.web;

import co.istad.dealkh.base.BaseResponse;
import co.istad.dealkh.features.discounttype.DiscountTypeService;
import co.istad.dealkh.features.discounttype.dto.DiscountTypeRequest;
import co.istad.dealkh.features.discounttype.dto.DiscountTypeResponse;
import co.istad.dealkh.features.discounttype.dto.DiscountTypeUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/discount-types")
public class DiscountTypeController {

    private final DiscountTypeService discountTypeService;

    // create discount type
    @PostMapping
    public BaseResponse<DiscountTypeResponse> createDiscountType(@RequestBody DiscountTypeRequest discountTypeRequest){
        return BaseResponse.<DiscountTypeResponse>createSuccess("Created new discount type")
                .setPayload(discountTypeService.createDiscountType(discountTypeRequest));
    }

    @GetMapping
    public BaseResponse<List<DiscountTypeResponse>> getAllDiscountType(){
        return BaseResponse.<List<DiscountTypeResponse>>ok("Get all discount types success")
                .setPayload(discountTypeService.getAllDiscountType());
    }

    // get discount type by name
    @GetMapping("/{name}")
    public BaseResponse<DiscountTypeResponse> getDiscountTypeByName(@PathVariable String name){
        return BaseResponse.<DiscountTypeResponse>ok("Get discount type by name success")
                .setPayload(discountTypeService.getDiscountTypeByName(name));
    }

    @PutMapping("/{name}")
    public BaseResponse<DiscountTypeResponse> updateDiscountType(@PathVariable String name, @RequestBody DiscountTypeUpdateRequest discountTypeUpdateRequest){
        return BaseResponse.<DiscountTypeResponse>ok("Update discount type success")
                .setPayload(discountTypeService.updateDiscountType(name, discountTypeUpdateRequest));
    }

    @DeleteMapping("/{name}")
    public BaseResponse<?> deleteDiscountType(@PathVariable String name){
        discountTypeService.deleteDiscountType(name);
        return BaseResponse.ok("Delete discount type success")
                .setPayload("No content");
    }

}
