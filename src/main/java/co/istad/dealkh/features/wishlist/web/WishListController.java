package co.istad.dealkh.features.wishlist.web;

import co.istad.dealkh.base.BaseResponse;
import co.istad.dealkh.features.wishlist.WishListService;
import co.istad.dealkh.features.wishlist.dto.WishListRequest;
import co.istad.dealkh.features.wishlist.dto.WishListResponse;
import co.istad.dealkh.paging.PageResponse;
import co.istad.dealkh.security.CustomUserDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

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
    public BaseResponse<WishListResponse> createWishList(@AuthenticationPrincipal CustomUserDetails customUserDetails, @RequestBody @Valid WishListRequest wishListRequest) {
        return BaseResponse.<WishListResponse>ok("Successfully create new wish list!!").setPayload(wishListService.addWishList(customUserDetails.getUsername(), wishListRequest));
    }

    @GetMapping
    PageResponse<WishListResponse> getAllWishList(@RequestParam Map<String, String> params) {
        return wishListService.getAllWishList(params);
    }


    @DeleteMapping("/{uuid}")
    BaseResponse<?> deleteWishList(@PathVariable String uuid) {
        wishListService.deleteWishList(uuid);
        return BaseResponse.ok("Successfully delete wish list with uuid:" + uuid).setPayload("");
    }

    @PostMapping("/{uuid}/grant")
    BaseResponse<WishListResponse> grantWishList(@PathVariable String uuid) {
        return BaseResponse.<WishListResponse>ok("Successfully grant wish list with uuid:" + uuid).setPayload(
                wishListService.grantWishListByUuid(uuid)
        );
    }

    @PostMapping("/{uuid}/deny")
    BaseResponse<WishListResponse> denyWishList(@PathVariable String uuid) {
        return BaseResponse.<WishListResponse>ok("Successfully grant wish list with uuid:" + uuid).setPayload(
                wishListService.denyWishListByUuid(uuid)
        );
    }

    @GetMapping("/{uuid}")
    BaseResponse<WishListResponse> getWishListByUuid(@PathVariable String uuid) {
        return BaseResponse.<WishListResponse>ok("Successfully get wish list with uuid:" + uuid).setPayload(
                wishListService.getWishListByUuid(uuid)
        );
    }

    @GetMapping("/{username}")
    BaseResponse<WishListResponse> getWishListByUsername(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        return BaseResponse.<WishListResponse>ok("Successfully get wish list with username:" + customUserDetails.getUsername()).setPayload(wishListService.getWishListByUsername(customUserDetails.getUsername()));
    }
}
