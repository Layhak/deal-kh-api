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
        String url,

        LocalDate expiredAt

) {

}
