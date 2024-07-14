package co.istad.dealkh.features.product.dto;

import co.istad.dealkh.features.image.dto.ImageResponse;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

/**
 * ProductResponse is a response object for a product.
 * It contains the ID, name, price, discount price, rating average, description, images, shop, discount percentage, category, created at, updated at, created by, and updated by fields.
 */
public record ProductResponse(

        String seller,
        String name,
        String slug,
        double price,
        double discountPrice,
        double ratingAvg,
        Long ratingCount,
        String description,
        List<ImageResponse> images,
        String shop,
        String shopSlug,
        String location,
        Long totalRating,
        LocalTime openAt,
        LocalTime closeAt,
        BigDecimal discountValue,
        Boolean isPercentage,
        String discountType,
        LocalDate expiredAt,
        String categoryName,
        String categorySlug,
        LocalDate createdAt,
        LocalDate updatedAt,
        String createdBy,
        String updatedBy

) {
}
