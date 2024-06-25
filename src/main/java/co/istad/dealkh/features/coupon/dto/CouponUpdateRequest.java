package co.istad.dealkh.features.coupon.dto;

import co.istad.dealkh.validator.localDate.ValidLocalDate;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CouponUpdateRequest(

        String description,
        BigDecimal value,
        LocalDate expiredAt,
        String image
) {
}
