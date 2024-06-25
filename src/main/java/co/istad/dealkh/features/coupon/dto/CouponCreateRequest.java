package co.istad.dealkh.features.coupon.dto;

import co.istad.dealkh.validator.localDate.ValidLocalDate;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CouponCreateRequest(

        String description,
        String image,

        @NotBlank(message = "Value is required")
        @Positive(message = "Value must be a positive number")
        BigDecimal value,

        @NotBlank(message = "Expired At is required")
        @ValidLocalDate
        String expiredAt,

        @NotBlank(message = "Shop slug is required")
        String shopSlug
) {
}
