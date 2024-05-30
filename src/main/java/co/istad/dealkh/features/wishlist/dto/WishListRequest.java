package co.istad.dealkh.features.wishlist.dto;

import jakarta.validation.constraints.NotNull;

public record WishListRequest(
        String description,

        @NotNull(message = "User id is required")
        Long userId,

        @NotNull(message = "Product id is required")
        Long productId
) {
}
