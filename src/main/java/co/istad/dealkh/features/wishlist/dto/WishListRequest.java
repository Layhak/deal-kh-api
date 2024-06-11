package co.istad.dealkh.features.wishlist.dto;

import co.istad.dealkh.validator.discount.DiscountRange;
import jakarta.validation.constraints.NotNull;

public record WishListRequest(

        @NotNull(message = "Discount id is required")
        Long discountTypeId,

        @DiscountRange(min = 0, max = 100, message = "Discount must be between 0 and 100")
        Integer discountPercentage,
        @NotNull(message = "User id is required")
        Long userId,

        @NotNull(message = "Product id is required")
        Long productId
) {
}
