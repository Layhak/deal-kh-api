package co.istad.dealkh.features.wishlist.dto;

import co.istad.dealkh.validator.discount.DiscountRange;
import co.istad.dealkh.validator.slug.ValidSlug;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record WishListRequest(

        @Positive(message = "Discount Percentage must be a positive number")
        @DiscountRange(min = 0, max = 100, message = "Discount must be between 0 and 100")
        BigDecimal discountPercentage,

        @NotNull(message = "Description is required")
        String description,

        @NotNull(message = "Product id is required")
        @Size(max = 100, message = "Product slug must be less than 100 characters")
        String productSlug
) {
}
