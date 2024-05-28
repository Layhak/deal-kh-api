package co.istad.dealkh.features.productrating.dto;

import co.istad.dealkh.domain.Product;
import co.istad.dealkh.domain.User;

public record ProductRatingResponse(
        Long id,
        double ratingValue,
        User user,
        Product product
) {
}

