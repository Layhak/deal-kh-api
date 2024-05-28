package co.istad.dealkh.features.shop.web;


import co.istad.dealkh.base.BaseResponse;
import co.istad.dealkh.features.shop.ShopService;

import co.istad.dealkh.features.shop.dto.ShopRequest;
import co.istad.dealkh.features.shop.dto.ShopResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/shops")
public class ShopController {
    private final ShopService shopService;

    @GetMapping
    @Operation(summary = "Get all shops")
    public BaseResponse<List<ShopResponse>> getAllShop() {
        return BaseResponse.<List<ShopResponse>>ok("Success ").setPayload(shopService.getAllShop());
    }

    @PostMapping
    @Operation(summary = "Create new shop")
    public BaseResponse<ShopResponse> createShop(@RequestBody ShopRequest shopRequest) {
        return BaseResponse.<ShopResponse>createSuccess("Created new shop").setPayload(shopService.createShop(shopRequest));
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Update shop")
    public BaseResponse<ShopResponse> updateShop(@PathVariable Long id, @RequestBody ShopRequest shopRequest) {
        return BaseResponse.<ShopResponse>updateSuccess("Updated shop").setPayload(shopService.updateShop(id, shopRequest));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get shop by id")
    public BaseResponse<ShopResponse> getShopById(@PathVariable Long id) {
        return BaseResponse.<ShopResponse>ok("Success").setPayload(shopService.getShopById(id));
    }

    @DeleteMapping("/{id}")
@Operation(summary = "Delete shop")
    public BaseResponse<Void> deleteShop(@PathVariable Long id) {
        shopService.deleteShop(id);
        return BaseResponse.<Void>deleteSuccess("Deleted shop");
    }

    @GetMapping("/nearby")
    @Operation(summary = "Get nearby shops")
    public BaseResponse<List<ShopResponse>> getNearbyShop(@RequestParam double latitude, @RequestParam double longitude) {
        return BaseResponse.<List<ShopResponse>>ok("Success").setPayload(shopService.getNearbyShop(latitude, longitude));
    }

    @GetMapping("/shop-type")
    @Operation(summary = "Get shops by shop type")
    public BaseResponse<List<ShopResponse>> getShopByShopType(@RequestParam String shopType) {
        return BaseResponse.<List<ShopResponse>>ok("Success").setPayload(shopService.getShopByShopType(shopType));
    }

    @PatchMapping("/{id}/disable")
    @Operation(summary = "Disable shop")
    public BaseResponse<ShopResponse> disableShop(@PathVariable Long id) {
        return BaseResponse.<ShopResponse>updateSuccess("Disabled shop").setPayload(shopService.disableShop(id));
    }

    @PatchMapping("/{id}/enable")
    @Operation(summary = "Enable shop")
    public BaseResponse<ShopResponse> enableShop(@PathVariable Long id) {
        return BaseResponse.<ShopResponse>updateSuccess("Enabled shop").setPayload(shopService.enableShop(id));
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "Get shops by user id")
    public BaseResponse<List<ShopResponse>> getShopByUserId(@PathVariable Long userId) {
        return BaseResponse.<List<ShopResponse>>ok("Success").setPayload(shopService.getShopByUserId(userId));
    }

    @GetMapping("/name")
    @Operation(summary = "Get shops by name")
    public BaseResponse<List<ShopResponse>> getShopByName(@RequestParam String name) {
        return BaseResponse.<List<ShopResponse>>ok("Success").setPayload(shopService.getShopByName(name));
    }


}
