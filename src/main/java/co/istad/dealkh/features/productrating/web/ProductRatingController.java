package co.istad.dealkh.features.productrating.web;

import co.istad.dealkh.base.BaseResponse;
import co.istad.dealkh.features.productrating.ProductRatingService;
import co.istad.dealkh.features.productrating.dto.ProductRatingCount;
import co.istad.dealkh.features.productrating.dto.ProductRatingRequest;
import co.istad.dealkh.features.productrating.dto.ProductRatingResponse;
import co.istad.dealkh.features.productrating.dto.ProductRatingUpdateRequest;
import co.istad.dealkh.security.CustomUserDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * ProductRatingController is a controller for managing product ratings.
 * It handles creating, retrieving, updating, and deleting product ratings.
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
@RequestMapping("/api/v1/product-ratings")
public class ProductRatingController {

    private final ProductRatingService productRatingService;

    /**
     * Rate a product based on the Request object.
     *
     * @param productRatingRequest
     * @return
     */
    @PostMapping()
    ProductRatingResponse rateProduct(@AuthenticationPrincipal CustomUserDetails customUserDetails, @RequestBody @Valid ProductRatingRequest productRatingRequest) {
        return productRatingService.rateProduct(customUserDetails.getUsername(), productRatingRequest);
    }

    /**
     * Retrieves all product ratings
     *
     * @return
     */
    @GetMapping()
    List<ProductRatingResponse> getAllProductRatings() {
        return productRatingService.getAllProductRating();
    }

    @PutMapping("/{productSlug}")
    ProductRatingResponse updateProductRating(@AuthenticationPrincipal CustomUserDetails customUserDetails, @PathVariable String productSlug, @RequestBody ProductRatingUpdateRequest productRatingUpdateRequest) {
        return productRatingService.updateProductRating(customUserDetails.getUsername(), productSlug, productRatingUpdateRequest);
    }

    @GetMapping("/{productSlug}")
    List<ProductRatingResponse> getProductRatingByProductSlug(@PathVariable String productSlug) {
        return productRatingService.getProductRatingByProductSlug(productSlug);
    }

    @GetMapping("/{slug}/count")
    BaseResponse<ProductRatingCount> getCountByProductSlug(@PathVariable String slug) {
        return BaseResponse.<ProductRatingCount>ok("Get all total product rating")
                .setPayload(productRatingService.countByProductSlug(slug));
    }
}
