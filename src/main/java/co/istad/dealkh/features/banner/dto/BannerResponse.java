package co.istad.dealkh.features.banner.dto;

import co.istad.dealkh.domain.Shop;


import java.time.LocalDate;

public record BannerResponse(
        String uuid,
        String name,
        String description,
        String image,
        String shopLink,
        LocalDate expiredAt,
        Boolean isExpired,
        String bannerType
) {
}
