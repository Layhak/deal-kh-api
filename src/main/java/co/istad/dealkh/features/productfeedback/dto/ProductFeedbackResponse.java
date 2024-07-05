package co.istad.dealkh.features.productfeedback.dto;

import co.istad.dealkh.features.image.dto.ImageResponse;

import java.util.List;

/**
 * ProductFeedbackResponse is a response object for a product feedback.
 * It contains the ID, description, product ID, user ID, and images.
 *
 * @param uuid
 * @param description
 * @param profile
 * @param productName
 * @param username
 * @param images
 */
public record ProductFeedbackResponse(
        String uuid,
        String profile,
        String description,
        String productName,
        String username,
        List<ImageResponse> images
) {
}
