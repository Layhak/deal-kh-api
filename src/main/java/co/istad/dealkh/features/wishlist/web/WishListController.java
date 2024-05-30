package co.istad.dealkh.features.wishlist.web;

import co.istad.dealkh.features.wishlist.WishListService;
import co.istad.dealkh.features.wishlist.dto.WishListRequest;
import co.istad.dealkh.features.wishlist.dto.WishListResponse;
import co.istad.dealkh.features.wishlist.dto.WishListUpdate;
import co.istad.dealkh.paging.PageResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/wishlists")
public class WishListController {

    private final WishListService wishListService;

    @PostMapping
    public WishListResponse createWishList(@RequestBody @Valid WishListRequest wishListRequest) {
        return wishListService.wishList(wishListRequest);
    }

    @GetMapping
    PageResponse<WishListResponse> getAllWishList(@RequestParam Map<String, String> params) {
        return wishListService.getAllWishList(params);
    }

    @PutMapping("/{id}")
    WishListResponse updateWishListById(@PathVariable Long id, @RequestBody @Valid WishListUpdate wishListUpdate) {
        return wishListService.updateWishListById(id, wishListUpdate);
    }

    @DeleteMapping("/{id}")
    void deleteWishList(@PathVariable Long id) {
        wishListService.deleteWishList(id);
    }

}
