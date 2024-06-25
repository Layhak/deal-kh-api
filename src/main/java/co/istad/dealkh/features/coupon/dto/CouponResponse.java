package co.istad.dealkh.features.coupon.dto;

import co.istad.dealkh.domain.Shop;
import co.istad.dealkh.features.shop.dto.ShopResponse;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CouponResponse(
        String description,
        String code,
        BigDecimal value,
        LocalDate expiredAt,
        Boolean isExpired,
        String shop,
        LocalDate createdAt,
        LocalDate updatedAt,
        String createdBy,
        String updatedBy,
        String image
) {
}
