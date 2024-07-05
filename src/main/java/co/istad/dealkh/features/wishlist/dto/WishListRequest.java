package co.istad.dealkh.features.wishlist.dto;

import co.istad.dealkh.validator.slug.ValidSlug;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record WishListRequest(

        @Positive(message = "Discount Percentage must be a positive number")
        Double discountPercentage,

        @NotNull(message = "Description is required")
        String description,

        @NotNull(message = "Product id is required")
        @Size(max = 100, message = "Product slug must be less than 100 characters")
        String productSlug
) {
}
