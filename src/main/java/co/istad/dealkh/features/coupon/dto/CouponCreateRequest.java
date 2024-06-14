package co.istad.dealkh.features.coupon.dto;

import co.istad.dealkh.validator.localDate.ValidLocalDate;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CouponCreateRequest(

        String description,

        @NotBlank(message = "Value is required")
        BigDecimal value,

        @NotBlank(message = "Expired At is required")
        LocalDate expiredAt,

        @NotBlank(message = "Shop id is required")
        String slug
) {
}
