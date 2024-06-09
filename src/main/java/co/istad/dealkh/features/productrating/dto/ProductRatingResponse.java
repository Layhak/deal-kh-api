package co.istad.dealkh.features.productrating.dto;

import java.time.LocalDateTime;

/**
 * ProductRatingResponse is a response object for a product rating.
 * It contains the ID, rating value, user ID, and created at date.
 *
 * @param id
 * @param ratingValue
 * @param userId
 * @param createdAt
 */
public record ProductRatingResponse(
        Long id,
        double ratingValue,
        Long userId,
        Long productId,
        LocalDateTime createdAt
) {
}

