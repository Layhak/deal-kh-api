package co.istad.dealkh.features.discounttype.dto;

import co.istad.dealkh.validator.name.ValidName;
import org.springframework.boot.context.properties.bind.DefaultValue;

public record DiscountTypeUpdateRequest(

        @ValidName(message = "Name must be properly formatted and can contain letters, numbers, single spaces, and single dashes")
        String name,

        @DefaultValue(value = "0")
        Integer sortOrder
) {
}
