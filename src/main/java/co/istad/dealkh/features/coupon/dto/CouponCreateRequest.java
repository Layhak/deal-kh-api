package co.istad.dealkh.features.coupon.dto;

import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CouponCreateRequest(

        String description,

        @NotBlank(message = "Code is required")
        String code,

        @NotBlank(message = "Value is required")
        BigDecimal value,

        @NotBlank(message = "Expired At is required")
        LocalDate expiredAt,

        @NotBlank(message = "Shop id is required")
        Long shopId
) {
}
