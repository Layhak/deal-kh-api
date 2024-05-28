package co.istad.dealkh.features.discount.dto;

import java.time.LocalDate;

public record DiscountResponseDetail(
        Long id,
        String name,
        String description,
        double discountPercentage,
        LocalDate expiredAt,
        LocalDate createdAt,
        LocalDate updatedAt
) {
}
