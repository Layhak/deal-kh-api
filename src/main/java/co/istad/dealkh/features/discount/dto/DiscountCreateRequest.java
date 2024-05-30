package co.istad.dealkh.features.discount.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record DiscountCreateRequest(

        @NotBlank(message = "Discount type is required")
        Long discountTypeId,

        String description,

        @NotNull(message = "Discount percentage is required")
        double discountPercentage,

        LocalDate expiredAt
) {
}
