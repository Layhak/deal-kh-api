package co.istad.dealkh.features.productrating.dto;

import co.istad.dealkh.validator.rating.OneOfRating;
import jakarta.validation.constraints.NotNull;

/**
 * ProductRatingRequest is a request object for creating a new product rating.
 * It contains the rating value and user ID.
 *
 * @param ratingValue
 * @param userId
 */
public record ProductRatingRequest(

        @OneOfRating(Values = {0, 0.5, 1, 1.5, 2, 2.5, 3, 3.5, 4, 4.5, 5}, message = "Rating must be between 0 and 5!")
        double ratingValue,

        @NotNull(message = "User id is required")
        Long userId,

        @NotNull(message = "Product id is required")
        Long productId
) {
}
