package co.istad.dealkh.features.product.dto;

import co.istad.dealkh.features.image.dto.ImageResponse;

import java.time.LocalDate;
import java.util.List;

public record ProductResponse(

        Long id,
        String name,
        double price,
        double ratingAvg,
        String description,
        List<ImageResponse> images,
        String shop,
        Double discountPercentage,
        String category,
        LocalDate createdAt,
        LocalDate updatedAt,
        String createdBy,
        String updateBy

) {
}
