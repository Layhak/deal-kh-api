package co.istad.dealkh.features.shoprating.dto;

import java.time.LocalDateTime;

public record ShopRatingResponse(
        double ratingValue,
        String username,
        String shop,
        boolean isRated,
        LocalDateTime createdAt
) {
}
