package co.istad.dealkh.features.discount.dto;

import co.istad.dealkh.validator.discount.DiscountRange;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * DiscountUpdateRequest is a request object for updating a discount.
 * It contains the discount percentage, expired at, and discount type ID.
 *
 * <p>This class uses the following annotations:
 * <ul>
 * <li>{@link NotNull} - Indicates that the discount percentage and expired at are required.</li>
 * </ul>
 * </p>
 */
public record DiscountUpdateRequest(

        String name,

        @NotNull(message = "Discount Type Id is required")
        Long discountTypeId,

        String description,

        @DiscountRange(min = 0, max = 100, message = "Discount must be between 0 and 100")
        BigDecimal value,

        LocalDate expiredAt

) {
}
