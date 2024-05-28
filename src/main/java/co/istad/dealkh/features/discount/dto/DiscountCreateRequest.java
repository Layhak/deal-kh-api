package co.istad.dealkh.features.discount.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record DiscountCreateRequest(

        @NotBlank(message = "Name is required")
         String name,

         String description,

        @NotNull(message = "Discount percentage is required")
         double discountPercentage,

         LocalDate expiredAt
) {
}
