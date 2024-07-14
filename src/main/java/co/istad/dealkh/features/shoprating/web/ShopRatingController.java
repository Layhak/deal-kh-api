package co.istad.dealkh.features.shoprating.web;

import co.istad.dealkh.base.BaseResponse;
import co.istad.dealkh.features.shoprating.ShopRatingService;
import co.istad.dealkh.features.shoprating.dto.ShopRatingCount;
import co.istad.dealkh.features.shoprating.dto.ShopRatingRequest;
import co.istad.dealkh.features.shoprating.dto.ShopRatingResponse;
import co.istad.dealkh.security.CustomUserDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/shop-ratings")
public class ShopRatingController {

    private final ShopRatingService shopRatingService;

    /**
     * Rate a shop based on the Request object.
     *
     * @param shopRatingRequest
     * @return
     */
    @PostMapping
    BaseResponse<ShopRatingResponse> rateProduct(@AuthenticationPrincipal CustomUserDetails customUserDetails, @RequestBody @Valid ShopRatingRequest shopRatingRequest) {
        return BaseResponse.<ShopRatingResponse>createSuccess("Successfully rate the shop!")
                        .setPayload(shopRatingService.rateShop(customUserDetails.getUsername(), shopRatingRequest));
    }

    /**
     * Retrieves all product ratings
     *
     * @return
     */
    @GetMapping
    BaseResponse<List<ShopRatingResponse>> getAllShopRatings() {
        return BaseResponse.<List<ShopRatingResponse>>ok("Successfully retrieved all the shop!")
                .setPayload(shopRatingService.getAllShopRating());
    }

    @GetMapping("/{shopSlug}")
    BaseResponse<List<ShopRatingResponse>> getAllShopRatingByShopSlug(@PathVariable String shopSlug) {
        return BaseResponse.<List<ShopRatingResponse>>ok("Successfully retrieved all the shop!")
                .setPayload(shopRatingService.getAllShopRatingByShopSlug(shopSlug));
    }

    @GetMapping("/{slug}/count")
    BaseResponse<ShopRatingCount> getCountByShopSlug(@PathVariable String slug) {
        return BaseResponse.<ShopRatingCount>ok("Get all total shop rating")
                .setPayload(shopRatingService.countByShopSlug(slug));
    }

    @DeleteMapping("/{shopSlug}")
    BaseResponse<?> deleteByRating(@AuthenticationPrincipal CustomUserDetails customUserDetails, @PathVariable String shopSlug) {
        shopRatingService.deleteByRating(customUserDetails.getUsername(), shopSlug);
        return BaseResponse.ok("Successfully deleted shop rating!")
                .setPayload("Delete Rating");
    }
}
