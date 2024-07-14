package co.istad.dealkh.features.shoprating.dto;

import co.istad.dealkh.validator.rating.OneOfRating;
import jakarta.validation.constraints.NotNull;

public record ShopRatingRequest(

        @OneOfRating(Values = {0, 0.5, 1, 1.5, 2, 2.5, 3, 3.5, 4, 4.5, 5}, message = "Rating must be between 0 and 5!")
        double ratingValue,

        @NotNull(message = "Shop slug is required")
        String shopSlug
) {
}
