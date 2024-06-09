package co.istad.dealkh.features.shoptype.web;

import co.istad.dealkh.base.BaseResponse;
import co.istad.dealkh.features.shoptype.ShopTypeService;
import co.istad.dealkh.features.shoptype.dto.ShopTypeCreateRequest;
import co.istad.dealkh.features.shoptype.dto.ShopTypeResponse;
import co.istad.dealkh.features.shoptype.dto.ShopTypeUpdateRequest;
import co.istad.dealkh.paging.PageResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/shoptypes")
public class ShopTypeController {

    private final ShopTypeService shopTypeService;

    @PostMapping("")
    BaseResponse<ShopTypeResponse> createShopType(@RequestBody @Valid ShopTypeCreateRequest shopTypeCreateRequest) {
        return BaseResponse.<ShopTypeResponse>createSuccess("Successfully created shop type!")
                .setPayload(shopTypeService.createShopType(shopTypeCreateRequest));
    }

    @GetMapping("/{name}")
    BaseResponse<Optional<ShopTypeResponse>> getShopTypeByName(@PathVariable String name) {
        return BaseResponse.<Optional<ShopTypeResponse>>ok("Successfully retrieved shop type!")
                .setPayload(shopTypeService.getShopTypeByName(name));
    }

    @GetMapping("")
    BaseResponse<PageResponse<ShopTypeResponse>> filterShopType(@RequestParam Map<String, String> params) {
        return BaseResponse.<PageResponse<ShopTypeResponse>>ok("Successfully retrieved shop type!")
                .setPayload(shopTypeService.filterShopTypes(params));
    }

    @PutMapping("/{name}")
    BaseResponse<ShopTypeResponse> updateShopTypeByName(@PathVariable String name, @RequestBody ShopTypeUpdateRequest shopTypeUpdateRequest) {
        return BaseResponse.<ShopTypeResponse>ok("Update shop type successfully!")
                .setPayload(shopTypeService.updateShopTypeByName(name, shopTypeUpdateRequest));
    }

    @DeleteMapping("/{name}")
    BaseResponse<?> deleteShopTypeByName(@PathVariable String name) {
        shopTypeService.deleteShopTypeByName(name);
        return BaseResponse.ok("Delete shop type successfully!");
    }

}
