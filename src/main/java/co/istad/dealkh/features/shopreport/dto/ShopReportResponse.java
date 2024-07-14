package co.istad.dealkh.features.shopreport.dto;

import co.istad.dealkh.features.image.dto.ImageResponse;

import java.util.List;

public record ShopReportResponse(
        String uuid,
        String profile,
        String description,
        String shop,
        String username,
        List<ImageResponse> images
) {
}
