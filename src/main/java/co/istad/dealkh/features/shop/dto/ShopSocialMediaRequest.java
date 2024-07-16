package co.istad.dealkh.features.shop.dto;

import jakarta.validation.constraints.NotNull;

public record ShopSocialMediaRequest(

        @NotNull(message = "Social name is required")
        String name,

        @NotNull(message = "Link is required")
        String link
) {
}
