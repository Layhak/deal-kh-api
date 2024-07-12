package co.istad.dealkh.features.wishlist.web;

import co.istad.dealkh.base.BaseResponse;
import co.istad.dealkh.domain.Product;
import co.istad.dealkh.features.wishlist.WishListService;
import co.istad.dealkh.features.wishlist.dto.WishListRequest;
import co.istad.dealkh.features.wishlist.dto.WishListResponse;
import co.istad.dealkh.paging.PageResponse;
import co.istad.dealkh.security.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * WishListController is a controller for managing wish lists.
 * It handles creating, retrieving, updating, and deleting wish lists.
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
@RequestMapping("/api/v1/wishlists")
public class WishListController {

    private final WishListService wishListService;

    @PostMapping
    @Operation(summary = "Create new wish list")
    public BaseResponse<WishListResponse> createWishList(@AuthenticationPrincipal CustomUserDetails customUserDetails, @RequestBody @Valid WishListRequest wishListRequest) {
        return BaseResponse.<WishListResponse>ok("Successfully create new wish list!!").setPayload(wishListService.addWishList(customUserDetails.getUsername(), wishListRequest));
    }

    @GetMapping
    @Operation(summary = "Get all wish lists")
    PageResponse<WishListResponse> getAllWishList(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "25") int size,
            @RequestParam(defaultValue = "discountPercentage") String field,
            @RequestParam(defaultValue = "asc") String order,
            @RequestParam Map<String, String> params
    ) {
        return wishListService.getAllWishList(page, size, field, order, params);
    }


    @PutMapping("/{uuid}")
    @Operation(summary = "Update wish list")
    public BaseResponse<WishListResponse> updateWishList(@AuthenticationPrincipal CustomUserDetails customUserDetails, @PathVariable String uuid, @RequestBody WishListRequest wishListRequest) {
        return BaseResponse.<WishListResponse>ok("Update wish list successfully!").setPayload(wishListService.updateWishList(uuid, customUserDetails.getUsername(), wishListRequest));
    }

    @DeleteMapping("/{uuid}")
    @Operation(summary = "Delete wish list")
    BaseResponse<?> deleteWishList(@AuthenticationPrincipal CustomUserDetails customUserDetails, @PathVariable String uuid) {
        wishListService.deleteWishList(customUserDetails.getUsername(), uuid);
        return BaseResponse.ok("Successfully delete wish list with uuid:" + uuid)
                .setPayload("Delete Wishlist");
    }

    @PostMapping("/{uuid}/grant")
    BaseResponse<WishListResponse> grantWishList(@AuthenticationPrincipal CustomUserDetails customUserDetails, @PathVariable String uuid) {
        return BaseResponse.<WishListResponse>ok("Successfully grant wish list with uuid:" + uuid).setPayload(
                wishListService.grantWishListByUuid(customUserDetails.getEmail(), uuid)
        );
    }

    @PostMapping("/{uuid}/deny")
    @Operation(summary = "Deny wish list")
    BaseResponse<WishListResponse> denyWishList(@PathVariable String uuid) {
        return BaseResponse.<WishListResponse>ok("Successfully grant wish list with uuid:" + uuid).setPayload(
                wishListService.denyWishListByUuid(uuid)
        );
    }

    @GetMapping("/{uuid}")
    @Operation(summary = "Get wish list by uuid")
    BaseResponse<WishListResponse> getWishListByUuid(@PathVariable String uuid) {
        return BaseResponse.<WishListResponse>ok("Successfully get wish list with uuid:" + uuid).setPayload(
                wishListService.getWishListByUuid(uuid)
        );
    }

    @GetMapping("/me")
    @Operation(summary = "Get wish list by username")
    BaseResponse<PageResponse<WishListResponse>> getWishListByUsername(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "25") int size,
            @RequestParam(defaultValue = "id") String field,
            @RequestParam(defaultValue = "asc") String order,
            @RequestParam Map<String, String> params,
            @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        return BaseResponse.<PageResponse<WishListResponse>>ok("Successfully get wish list with username:" + customUserDetails.getUsername()).setPayload(wishListService.getWishListByUsername(page, size, field, order, params, customUserDetails.getUsername()));
    }

    @GetMapping("/{slug}/shop")
    @Operation(summary = "Get wish list by username")
    BaseResponse<List<WishListResponse>> getWishListByUsername(
            @PathVariable String slug) {
        return BaseResponse.<List<WishListResponse>>ok("Successfully get all wish list!").setPayload(wishListService.getWishListByShop(slug));
    }



}
