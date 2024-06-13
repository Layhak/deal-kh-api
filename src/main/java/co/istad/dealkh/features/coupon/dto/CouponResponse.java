package co.istad.dealkh.features.coupon.dto;

import co.istad.dealkh.domain.Shop;
import co.istad.dealkh.features.shop.dto.ShopResponse;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CouponResponse(
        Long id,
        String description,
        String code,
        BigDecimal value,
        LocalDate expiredAt,
        Boolean isExpired,
        String shop
) {
}
