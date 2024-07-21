package co.istad.dealkh.features.discounttype.dto;

import co.istad.dealkh.validator.name.ValidName;
import co.istad.dealkh.validator.slug.ValidSlug;
import org.springframework.boot.context.properties.bind.DefaultValue;

public record DiscountTypeRequest(

        @ValidName(message = "Name must be properly formatted and can contain letters, numbers, single spaces, and single dashes")
        String name,

        @ValidSlug(message = "Slug must be properly formatted and can contain lowercase letters, numbers, and single dashes")
        String slug,

        @DefaultValue(value = "0")
        Integer sortOrder
) {
}
