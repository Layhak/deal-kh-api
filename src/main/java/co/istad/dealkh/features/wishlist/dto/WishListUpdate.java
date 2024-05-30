package co.istad.dealkh.features.wishlist.dto;

import jakarta.validation.constraints.NotNull;

public record WishListUpdate(
        String description,

        Long userId,

        Long productId
) {
}
