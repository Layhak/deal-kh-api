package co.istad.dealkh.features.productfeedback.dto;

import co.istad.dealkh.features.image.dto.ImageResponse;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record ProductFeedbackRequest(
        String description,

        @NotNull(message = "Product id is required")
        Long productId,

        @NotNull(message = "User id is required")
        Long userId,

        @NotNull(message = "Images is required")
        List<ImageResponse> images

) {
}
