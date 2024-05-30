package co.istad.dealkh.features.discount.dto;

import co.istad.dealkh.validator.OneOfDiscount;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record DiscountCreateRequest(


        String description,

        @OneOfDiscount(Values = {0, 10, 20, 30, 40, 50, 60, 70, 80, 90, 100}, message = "Discount Percentage must be one of:{Values}")
        Integer discountPercentage,

        @NotNull(message = "Expired At is required")
        LocalDate expiredAt,

        @NotNull(message = "Discount Type Id is required")
        Long discountTypeId
) {
}
