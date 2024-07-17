package co.istad.dealkh.features.banner.dto;

import co.istad.dealkh.domain.Shop;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record BannerCreateRequest(
        String name,

        String description,

        @NotNull(message = "Image is required")
        String image,

        @NotNull(message = "Url is required")
        String shopLink,

        @NotNull(message = "Expired At is required")
        LocalDate expiredAt,

        @NotNull(message = "Banner Type is required")
        String bannerType

) {

}
