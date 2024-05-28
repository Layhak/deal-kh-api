package co.istad.dealkh.features.productrating.web;

import co.istad.dealkh.features.productrating.ProductRatingService;
import co.istad.dealkh.features.productrating.dto.ProductRatingRequest;
import co.istad.dealkh.features.productrating.dto.ProductRatingResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/vq/product-ratings")
public class ProductRatingController {

    private final ProductRatingService productRatingService;

    @PostMapping("/")
    ProductRatingResponse rateProduct(@RequestBody @Valid ProductRatingRequest productRatingRequest) {
        return productRatingService.rateProduct(productRatingRequest);
    }

    @GetMapping("/")
    List<ProductRatingResponse> getAllProductRatings() {
        return productRatingService.getAllProductRating();
    }
}
