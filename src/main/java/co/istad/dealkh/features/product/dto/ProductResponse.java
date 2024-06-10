package co.istad.dealkh.features.product.dto;

import co.istad.dealkh.features.image.dto.ImageResponse;

import java.time.LocalDate;
import java.util.List;

/**
 * ProductResponse is a response object for a product.
 * It contains the ID, name, price, discount price, rating average, description, images, shop, discount percentage, category, created at, updated at, created by, and updated by fields.
 */
public record ProductResponse(

        Long id,
        String name,
        double price,
        double discountPrice,
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
