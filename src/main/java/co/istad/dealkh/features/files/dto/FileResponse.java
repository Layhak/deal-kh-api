package co.istad.dealkh.features.files.dto;

import lombok.Builder;

@Builder
public record FileResponse(
        String filename,
        String fullUrl,
        String downloadUrl,
        String fileType,
        float size) {
}
