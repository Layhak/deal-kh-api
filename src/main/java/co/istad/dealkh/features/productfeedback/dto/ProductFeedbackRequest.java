package co.istad.dealkh.features.productfeedback.dto;

import co.istad.dealkh.domain.json.Image;

import java.util.List;

public record ProductFeedbackRequest(
        String description,
        Long productId,

        Long userId,
        List<Image> images

) {
}
