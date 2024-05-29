package co.istad.dealkh.features.productrating.dto;

import co.istad.dealkh.domain.Product;
import co.istad.dealkh.domain.User;

import java.time.LocalDateTime;

public record ProductRatingResponse(
        Long id,
        double ratingValue,
        Long userId,
        Long productId,
        LocalDateTime createdAt
) {
}

