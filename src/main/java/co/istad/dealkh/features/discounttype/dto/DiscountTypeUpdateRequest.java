package co.istad.dealkh.features.discounttype.dto;

import co.istad.dealkh.validator.name.ValidName;

public record DiscountTypeUpdateRequest(

        @ValidName(message = "Name must be properly formatted and can contain letters, numbers, single spaces, and single dashes")
        String name
) {
}
