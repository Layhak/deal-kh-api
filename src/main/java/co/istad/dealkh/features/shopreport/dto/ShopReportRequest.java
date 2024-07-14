package co.istad.dealkh.features.shopreport.dto;

import co.istad.dealkh.features.image.dto.ImageRequest;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record ShopReportRequest(
        @NotNull(message = "Description is required")
        String description,

        @NotNull(message = "Shop slug is required")
        String shopSlug,

        List<ImageRequest> images
) {
}
