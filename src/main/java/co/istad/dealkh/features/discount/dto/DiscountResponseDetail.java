package co.istad.dealkh.features.discount.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * DiscountResponseDetail is a response object for a discount type.
 * It contains the ID, name, and description fields.
 *
 * @param uuid
 * @param discountType
 * @param description
 * @param discountValue
 * @param expiredAt
 * @param createdAt
 * @param updatedAt
 */
public record DiscountResponseDetail(
        String uuid,
        String discountType,
        String description,
        BigDecimal discountValue,
        Boolean isPercentage,
        LocalDate expiredAt,
        LocalDate createdAt,
        LocalDate updatedAt,
        String createdBy,
        String updatedBy,
        String shop
) {
}
