package co.istad.dealkh.features.wishlist.dto;

import java.math.BigDecimal;

public record WishListResponse(
        String uuid,
        String profile,
        String productName,
        String username,
        BigDecimal discountPercentage,
        String description,
        String isGranted
) {
}
