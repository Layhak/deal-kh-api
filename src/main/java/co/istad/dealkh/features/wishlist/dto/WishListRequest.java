package co.istad.dealkh.features.wishlist.dto;

import co.istad.dealkh.validator.OneOfDiscount;
import jakarta.validation.constraints.NotNull;

public record WishListRequest(

        @NotNull(message = "Discount id is required")
        Long discountTypeId,

        @OneOfDiscount(Values = {10, 20, 30, 40, 50, 60, 70, 80, 90, 100}, message = "Discount percentage must be one of {Values}")
        Integer discountPercentage,
        @NotNull(message = "User id is required")
        Long userId,

        @NotNull(message = "Product id is required")
        Long productId
) {
}
