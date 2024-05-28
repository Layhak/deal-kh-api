package co.istad.dealkh.features.shoptype.dto;

import jakarta.validation.constraints.NotBlank;

public record ShopTypeCreateRequest(

        @NotBlank(message = "Name is required")
        String name,

        String icon
) {
}
