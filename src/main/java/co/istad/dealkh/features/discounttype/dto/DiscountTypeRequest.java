package co.istad.dealkh.features.discounttype.dto;

import co.istad.dealkh.domain.Discount;
import co.istad.dealkh.validator.slug.ValidSlug;
import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;

import java.util.List;

public record DiscountTypeRequest (
        String name,

        @ValidSlug(message = "Slug must be properly formatted and can contain lowercase letters, numbers, and single dashes")
        String slug
) {
}
