package co.istad.dealkh.features.shoptype;

import co.istad.dealkh.features.shoptype.dto.ShopTypeRequest;
import co.istad.dealkh.features.shoptype.dto.ShopTypeResponse;
import co.istad.dealkh.utils.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/shoptype")
public class ShopTypeController {
    private final ShopTypeService shopTypeService;

    @GetMapping
    BaseResponse<List<ShopTypeResponse>> getShopType() {
        return BaseResponse.<List<ShopTypeResponse>>ok("Success ").setPayload(shopTypeService.getAllShopTypes());
    }

    @PostMapping
    public BaseResponse<ShopTypeResponse> createShopType(@RequestBody ShopTypeRequest shopTypeRequest) {
        return BaseResponse.<ShopTypeResponse>createSuccess("Created new shop type").setPayload(shopTypeService.createShopType(shopTypeRequest));
    }

    @PatchMapping("/{id}")
    public BaseResponse<ShopTypeResponse> updateShopType(@PathVariable Long id, @RequestBody ShopTypeRequest shopTypeRequest) {
        return BaseResponse.<ShopTypeResponse>createSuccess("Updated shop type").setPayload(shopTypeService.updateShopType(id, shopTypeRequest));
    }

    @DeleteMapping("/{id}")
    public BaseResponse<String> deleteShopType(@PathVariable Long id) {
        shopTypeService.deleteShopType(id);
        return BaseResponse.<String>createSuccess("Deleted shop type");
    }

    @GetMapping("/{id}")
    public BaseResponse<ShopTypeResponse> getShopTypeById(@PathVariable Long id) {
        return BaseResponse.<ShopTypeResponse>ok("Success").setPayload(shopTypeService.getShopTypeById(id));
    }

    @GetMapping("/name/{name}")
    public BaseResponse<ShopTypeResponse> getShopTypeByName(@PathVariable String name) {
        return BaseResponse.<ShopTypeResponse>ok("Success").setPayload(shopTypeService.getShopTypeByName(name));
    }

}
