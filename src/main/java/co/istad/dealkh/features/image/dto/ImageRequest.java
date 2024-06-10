package co.istad.dealkh.features.image.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ImageRequest(

        @NotBlank(message = "Url is required")
        String url
) {
}
