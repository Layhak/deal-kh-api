package co.istad.dealkh.features.productfeedback.dto;

import co.istad.dealkh.features.image.dto.ImageResponse;
import jakarta.validation.constraints.NotNull;

import java.util.List;

/**
 * ProductFeedbackRequest is a request object for creating a new product feedback.
 * It contains the description, product ID, user ID, and images.
 *
 * @param description
 * @param productId
 * @param userId
 * @param images
 */
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
