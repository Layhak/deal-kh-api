package co.istad.dealkh.features.wishlist.dto;

import co.istad.dealkh.validator.slug.ValidSlug;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record WishListRequest(

        @NotNull(message = "Discount Type Slug is required")
        @ValidSlug
        String discountTypeSlug,

        Double discountPercentage,

        @NotNull(message = "Product id is required")
        @Size(max = 100, message = "Product slug must be less than 100 characters")
        String productSlug
) {
}
