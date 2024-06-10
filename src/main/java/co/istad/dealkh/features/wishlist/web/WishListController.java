package co.istad.dealkh.features.wishlist.web;

import co.istad.dealkh.base.BaseResponse;
import co.istad.dealkh.features.wishlist.WishListService;
import co.istad.dealkh.features.wishlist.dto.WishListRequest;
import co.istad.dealkh.features.wishlist.dto.WishListResponse;
import co.istad.dealkh.paging.PageResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
    public BaseResponse<WishListResponse> createWishList(@RequestBody @Valid WishListRequest wishListRequest) {
        return BaseResponse.<WishListResponse>ok("Successfully create new wish list!!").setPayload(wishListService.addWishList(wishListRequest));
    }

    @GetMapping
    PageResponse<WishListResponse> getAllWishList(@RequestParam Map<String, String> params) {
        return wishListService.getAllWishList(params);
    }


    @DeleteMapping("/{id}")
    BaseResponse<?> deleteWishList(@PathVariable Long id) {
        wishListService.deleteWishList(id);
        return BaseResponse.ok("Successfully delete wish list with id:" + id).setPayload("");
    }

    @PostMapping("/{id}/grant")
    BaseResponse<WishListResponse> grantWishList(@PathVariable Long id) {
        return BaseResponse.<WishListResponse>ok("Successfully grant wish list with id:" + id).setPayload(
                wishListService.grantWishList(id)
        );
    }

    @PostMapping("/{id}/deny")
    BaseResponse<WishListResponse> denyWishList(@PathVariable Long id) {
        return BaseResponse.<WishListResponse>ok("Successfully grant wish list with id:" + id).setPayload(
                wishListService.denyWishList(id)
        );
    }
}
