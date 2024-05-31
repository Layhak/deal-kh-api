package co.istad.dealkh.features.discount.dto;

import java.time.LocalDate;

public record DiscountTypeResponse(
        Long id,
        String discountType,
        String description,
        Integer discountPercentage,
        LocalDate expiredAt,
        LocalDate createdAt,
        LocalDate updatedAt
) {
}
