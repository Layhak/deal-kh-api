package co.istad.dealkh.features.coupon.dto;

import co.istad.dealkh.validator.localDate.ValidLocalDate;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CouponUpdateRequest(

        String description,
        BigDecimal value,

//        @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "ExpiredAt must be in the format yyyy-MM-dd")
        LocalDate expiredAt
) {
}
