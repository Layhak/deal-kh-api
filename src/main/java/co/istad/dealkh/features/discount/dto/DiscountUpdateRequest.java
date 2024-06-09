package co.istad.dealkh.features.discount.dto;

import co.istad.dealkh.validator.discount.OneOfDiscount;
import jakarta.validation.constraints.NotNull;

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

        @NotNull(message = "Discount Type Id is required")
        Long discountTypeId,
        String description,
        @OneOfDiscount(Values = {0, 10, 20, 30, 40, 50, 60, 70, 80, 90, 100}, message = "Discount Percentage must be one of:{Values}")
        Integer discountPercentage,
        LocalDate expiredAt

) {
}
