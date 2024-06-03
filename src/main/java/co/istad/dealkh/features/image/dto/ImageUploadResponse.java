package co.istad.dealkh.features.image.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ImageUploadResponse {
    private String downloadUrl;
    private String fileType;
    private float size;
    private String filename;
    private String fullUrl;
    private String description; // Add this field to hold image descriptions
}
