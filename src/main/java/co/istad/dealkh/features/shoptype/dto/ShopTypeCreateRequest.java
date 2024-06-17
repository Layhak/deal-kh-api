package co.istad.dealkh.features.shoptype.dto;

import co.istad.dealkh.validator.name.ValidName;
import jakarta.validation.constraints.NotBlank;

public record ShopTypeCreateRequest(

        @NotBlank(message = "Name is required")
        @ValidName(message = "Name must be properly formatted and can contain letters, numbers, single spaces, and single dashes")
        String name,

        String icon
) {
}
