package co.istad.dealkh.features.coupon.dto;

import co.istad.dealkh.validator.localDate.ValidLocalDate;
import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;

public record CouponCreateRequest(

        String description,

        @NotBlank(message = "Value is required")
        BigDecimal value,

        @NotBlank(message = "Expired At is required")
        @ValidLocalDate
        String expiredAt,

        @NotBlank(message = "Shop slug is required")
        String shopSlug
) {
}
