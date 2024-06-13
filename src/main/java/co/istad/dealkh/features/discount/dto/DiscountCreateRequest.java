package co.istad.dealkh.features.discount.dto;

import co.istad.dealkh.validator.discount.DiscountRange;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * DiscountCreateRequest is a request object for creating a new discount.
 * It contains the discount percentage, expired at, and discount type ID.
 *
 * <p>This class uses the following annotations:
 * <ul>
 * <li>{@link NotNull} - Indicates that the discount percentage and expired at are required.</li>
 * </ul>
 * </p>
 */
public record DiscountCreateRequest(


        String description,

        @DiscountRange(min = 0, max = 100, message = "Discount must be between 0 and 100")
        BigDecimal discountValue,

        @NotNull(message = "Expired At is required")
        LocalDate expiredAt,

        @NotNull(message = "Discount Type Id is required")
        Long discountTypeId,

        @NotNull(message = "Shop Id is required")
        Long shopId
) {
}
