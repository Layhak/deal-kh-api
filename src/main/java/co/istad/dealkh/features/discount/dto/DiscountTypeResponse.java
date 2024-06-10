package co.istad.dealkh.features.discount.dto;

import java.time.LocalDate;

/**
 * DiscountTypeResponse is a response object for a discount type.
 * It contains the ID, name, and description fields.
 *
 * @param id
 * @param discountType
 * @param description
 * @param discountPercentage
 * @param expiredAt
 * @param createdAt
 * @param updatedAt
 */
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
