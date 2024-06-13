package co.istad.dealkh.features.shop.web;


import co.istad.dealkh.base.BaseResponse;
import co.istad.dealkh.features.shop.ShopService;
import co.istad.dealkh.features.shop.dto.ShopRequest;
import co.istad.dealkh.features.shop.dto.ShopResponse;
import co.istad.dealkh.features.shop.dto.ShopUpdateRequest;
import co.istad.dealkh.paging.PageResponse;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * ShopController is a controller for managing shops.
 * It handles creating, retrieving, updating, and deleting shops.
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
@RequestMapping("/api/v1/shops")
public class ShopController {
    private final ShopService shopService;

    @GetMapping
    @Operation(summary = "Get all shops")
    public BaseResponse<PageResponse<ShopResponse>> getAllShop(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "2") int size,
            @RequestParam(defaultValue = "name") String field,
            @RequestParam(defaultValue = "asc") String order
    ) {
        return BaseResponse.<PageResponse<ShopResponse>>ok("Success").setPayload(shopService.getAllShop(page, size, field, order));
    }

    @PostMapping
    @Operation(summary = "Create new shop")
    public BaseResponse<ShopResponse> createShop(@RequestBody @Valid ShopRequest shopRequest) {
        return BaseResponse.<ShopResponse>createSuccess("Created new shop").setPayload(shopService.createShop(shopRequest));
    }

    @PutMapping("/{name}")
    @Operation(summary = "Update shop")
    public BaseResponse<ShopResponse> updateShop(@PathVariable String name, @RequestBody ShopUpdateRequest shopUpdateRequest) {
        return BaseResponse.<ShopResponse>ok("Updated shop").setPayload(shopService.updateShop(name, shopUpdateRequest));
    }

    @GetMapping("/{name}")
    @Operation(summary = "Get shop by name")
    public BaseResponse<ShopResponse> getShopByName(@PathVariable String name) {
        return BaseResponse.<ShopResponse>ok("Success").setPayload(shopService.getShopByName(name));
    }

    @DeleteMapping("/{name}")
    @Operation(summary = "Delete shop")
    public BaseResponse<?> deleteShop(@PathVariable String name) {
        shopService.deleteShop(name);
        return BaseResponse.ok("Deleted shop")
                .setPayload("No content");
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

    @PatchMapping("/{name}/disable")
    @Operation(summary = "Disable shop")
    public BaseResponse<ShopResponse> disableShop(@PathVariable String name) {
        return BaseResponse.<ShopResponse>ok("Disabled shop").setPayload(shopService.disableShop(name));
    }

    @PatchMapping("/{name}/enable")
    @Operation(summary = "Enable shop")
    public BaseResponse<ShopResponse> enableShop(@PathVariable String name) {
        return BaseResponse.<ShopResponse>ok("Enabled shop").setPayload(shopService.enableShop(name));
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "Get shops by user id")
    public BaseResponse<List<ShopResponse>> getShopByUserId(@PathVariable Long userId) {
        return BaseResponse.<List<ShopResponse>>ok("Success").setPayload(shopService.getShopByUserId(userId));
    }

    @GetMapping("/name")
    @Operation(summary = "Get shops by name")
    public BaseResponse<List<ShopResponse>> getAllShopByName(@RequestParam String name) {
        return BaseResponse.<List<ShopResponse>>ok("Success").setPayload(shopService.getAllShopByName(name));
    }


}
