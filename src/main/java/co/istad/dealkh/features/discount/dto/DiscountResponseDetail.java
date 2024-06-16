package co.istad.dealkh.features.discount.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * DiscountResponseDetail is a response object for a discount type.
 * It contains the ID, name, and description fields.
 *
 * @param id
 * @param discountType
 * @param description
 * @param value
 * @param expiredAt
 * @param createdAt
 * @param updatedAt
 */
public record DiscountResponseDetail(
        String uuid,
        String discountType,
        String description,
        BigDecimal value,
        LocalDate expiredAt,
        LocalDate createdAt,
        LocalDate updatedAt,
        String shopName
) {
}
