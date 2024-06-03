package co.istad.dealkh.features.image.dto;

public record ImageResponse(
        String filename,
        String fullUrl,
        String downloadUrl,
        String fileType,
        float size
) {
}
