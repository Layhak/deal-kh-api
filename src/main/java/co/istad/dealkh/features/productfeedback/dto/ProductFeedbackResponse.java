package co.istad.dealkh.features.productfeedback.dto;

import co.istad.dealkh.features.image.dto.ImageResponse;

import java.util.List;

public record ProductFeedbackResponse(
        Long id,
        String description,
        Long productId,
        Long userId,
        List<ImageResponse> images
) {
}
