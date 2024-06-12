package co.istad.dealkh.features.coupon.dto;

import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CouponUpdateRequest(

        String description,

        @NotBlank(message = "Code is required")
        String code,

        @NotBlank(message = "Value is required")
        BigDecimal value,

        LocalDate expiredAt,

        Boolean isExpired
) {
}
