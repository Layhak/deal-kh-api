package co.istad.dealkh.features.productrating.dto;

import jakarta.validation.constraints.NotNull;

public record ProductRatingRequest(

        double ratingValue,

        @NotNull(message = "User id is required")
        Long userId,

        @NotNull(message = "Product id is required")
        Long productId
) {
}
