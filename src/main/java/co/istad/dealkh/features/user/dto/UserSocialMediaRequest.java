package co.istad.dealkh.features.user.dto;

import jakarta.validation.constraints.NotNull;

public record UserSocialMediaRequest(

        String name,

        @NotNull(message = "Link is required")
        String link
) {
}
