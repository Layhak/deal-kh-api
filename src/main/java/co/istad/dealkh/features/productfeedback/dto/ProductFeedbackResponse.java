package co.istad.dealkh.features.productfeedback.dto;

import co.istad.dealkh.features.image.dto.ImageResponse;

import java.util.List;

/**
 * ProductFeedbackResponse is a response object for a product feedback.
 * It contains the ID, description, product ID, user ID, and images.
 *
 * @param id
 * @param description
 * @param productId
 * @param userId
 * @param images
 */
public record ProductFeedbackResponse(
        Long id,
        String description,
        Long productId,
        Long userId,
        List<ImageResponse> images
) {
}
