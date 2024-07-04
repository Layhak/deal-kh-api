package co.istad.dealkh.features.shop.web;


import co.istad.dealkh.base.BaseResponse;
import co.istad.dealkh.features.shop.ShopService;
import co.istad.dealkh.features.shop.dto.*;
import co.istad.dealkh.features.user.dto.UserProfileRequest;
import co.istad.dealkh.features.user.dto.UserProfileResponse;
import co.istad.dealkh.paging.PageResponse;
import co.istad.dealkh.security.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<PageResponse<ShopResponse>> getAllShop(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "25") int size,
            @RequestParam(defaultValue = "name") String field,
            @RequestParam(defaultValue = "asc") String order
    ) {
        return BaseResponse.<PageResponse<ShopResponse>>ok("Successfully retrieved all shops!")
                .setPayload(shopService.getAllShop(page, size, field, order));
    }

    @PostMapping
    @Operation(summary = "Create new shop")
    @ResponseStatus(HttpStatus.CREATED)
    public BaseResponse<ShopResponse> createShop(@RequestBody @Valid ShopCreateRequest shopRequest, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        return BaseResponse.<ShopResponse>createSuccess("Successfully created new shop!")
                .setPayload(shopService.createShop(shopRequest, List.of(customUserDetails.getUsername())));
    }


    @PatchMapping("/{slug}")
    @Operation(summary = "Update shop")
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<ShopResponse> updateShop(@AuthenticationPrincipal CustomUserDetails customUserDetails, @PathVariable String slug, @RequestBody ShopUpdateRequest shopRequest) {
        return BaseResponse.<ShopResponse>ok("Successfully updated shop!")
                .setPayload(shopService.updateShop(slug, shopRequest, customUserDetails.getUsername()));
    }

    @GetMapping("/{slug}")
    @Operation(summary = "Get shop by slug")
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<ShopResponse> getShopById(@PathVariable String slug) {
        return BaseResponse.<ShopResponse>ok("Successfully retrieved shop!")
                .setPayload(shopService.getShopBySlug(slug));
    }

    @DeleteMapping("/{slug}")
    @Operation(summary = "Delete shop")
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<?> deleteShop(@PathVariable String slug, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        shopService.deleteShop(slug, customUserDetails.getUsername());
        return BaseResponse.ok("Successfully deleted shop!").setPayload(new ArrayList<>());
    }

    @GetMapping("/nearby")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get nearby shops")
    public BaseResponse<List<ShopResponse>> getNearbyShop(@RequestParam double latitude, @RequestParam double longitude) {
        return BaseResponse.<List<ShopResponse>>ok("Successfully retrieved nearby shops!")
                .setPayload(shopService.getNearbyShop(latitude, longitude));
    }

    @GetMapping("/shop-type")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get shops by shop type")
    public BaseResponse<List<ShopResponse>> getShopByShopType(@RequestParam String shopType) {
        return BaseResponse.<List<ShopResponse>>ok("Successfully retrieved shops by shop type!")
                .setPayload(shopService.getShopByShopType(shopType));

    }

    @PatchMapping("/{slug}/disable")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Disable shop")
    public BaseResponse<ShopResponse> disableShop(@PathVariable String slug, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        return BaseResponse.<ShopResponse>ok("Successfully disabled shop!")
                .setPayload(shopService.disableShop(slug, customUserDetails.getUsername()));
    }

    @PatchMapping("/{slug}/enable")
    @Operation(summary = "Enable shop")
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<ShopResponse> enableShop(@PathVariable String slug, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        return BaseResponse.<ShopResponse>ok("Successfully enabled shop!")
                .setPayload(shopService.enableShop(slug, customUserDetails.getUsername()));
    }

    @GetMapping("/owner")
    @Operation(summary = "Get all shops created by the logged-in user")
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<PageResponse<ShopResponse>> getAllOwnerShops(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "25") int size,
            @RequestParam(defaultValue = "name") String field,
            @RequestParam(defaultValue = "asc") String order
    ) {
        return BaseResponse.<PageResponse<ShopResponse>>ok("Successfully retrieved all shops created by the logged-in user!")
                .setPayload(shopService.getAllOwnerShop(page, size, field, order, customUserDetails.getUsername()));
    }

    @GetMapping("/{slug}/owner")
    @Operation(summary = "Get all shops created by the logged-in user")
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<ShopResponse> getOwnerShops(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @PathVariable String slug
    ) {
        return BaseResponse.<ShopResponse>ok("Successfully retrieved all shops created by the logged-in user!")
                .setPayload(shopService.getOwnerShopBySlug(slug, customUserDetails.getUsername()));
    }

    @PostMapping("/{slug}/owner")
    @Operation(summary = "Add owner to shop")
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<ShopResponse> addOwnerToShop(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @PathVariable String slug,
            @RequestParam String username
    ) {
        return BaseResponse.<ShopResponse>ok("Successfully add owner to shop!")
                .setPayload(shopService.addOwnerToShop(slug, username, customUserDetails.getUsername()));
    }

    @DeleteMapping("/{slug}/owner")
    @Operation(summary = "Remove owner from shop")
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<ShopResponse> removeOwnerFromShop(
            @PathVariable String slug,
            @RequestParam String username,
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ) {
        return BaseResponse.<ShopResponse>ok("Successfully remove owner from shop!")
                .setPayload(shopService.removeOwnerFromShop(slug, username, customUserDetails.getUsername()));
    }

    @GetMapping("/{slug}/profile")
    @Operation(summary = "Get shop profile")
    public BaseResponse<ShopProfileResponse> getShopProfile(@PathVariable String slug) {
        return BaseResponse.<ShopProfileResponse>ok("Successfully get shop profile!")
                .setPayload(shopService.getShopProfile(slug));
    }

    @PostMapping("/{slug}/profile")
    @Operation(summary = "Upload shop profile")
    public BaseResponse<ShopProfileResponse> uploadShopProfile(@AuthenticationPrincipal CustomUserDetails customUserDetails, @PathVariable String slug,  @RequestBody ShopProfileRequest shopProfileRequest) {
        return BaseResponse.<ShopProfileResponse>ok("Successfully upload shop profile!")
                .setPayload(shopService.uploadShopProfile(customUserDetails.getUsername(), slug, shopProfileRequest));
    }

    @DeleteMapping("/{slug}/profile")
    @Operation(summary = "Delete shop profile")
    public BaseResponse<?> deleteShopProfile(@AuthenticationPrincipal CustomUserDetails customUserDetails, @PathVariable String slug, @RequestBody String profile) {
        shopService.deleteShopProfile(customUserDetails.getUsername(),slug, profile);
        return BaseResponse.ok("Profile has been remove!");
    }



    @GetMapping("/{slug}/cover")
    @Operation(summary = "Get shop cover")
    public BaseResponse<ShopCoverResponse> getShopCover(@PathVariable String slug) {
        return BaseResponse.<ShopCoverResponse>ok("Successfully get shop profile!")
                .setPayload(shopService.getAllShopCover(slug));
    }

    @PostMapping("/{slug}/cover")
    @Operation(summary = "Upload shop cover")
    public BaseResponse<ShopCoverResponse> uploadShopCover(@AuthenticationPrincipal CustomUserDetails customUserDetails, @PathVariable String slug,  @RequestBody ShopCoverRequest shopCoverRequest) {
        return BaseResponse.<ShopCoverResponse>ok("Successfully upload shop profile!")
                .setPayload(shopService.uploadShopCover(customUserDetails.getUsername(), slug, shopCoverRequest));
    }

    @DeleteMapping("/{slug}/cover")
    @Operation(summary = "Delete shop cover")
    public BaseResponse<?> deleteShopCover(@AuthenticationPrincipal CustomUserDetails customUserDetails, @PathVariable String slug, @RequestBody ShopCoverRequest shopCoverRequest) {
        shopService.deleteShopCover(customUserDetails.getUsername(),slug, shopCoverRequest);
        return BaseResponse.ok("Cover has been remove!");
    }

}
