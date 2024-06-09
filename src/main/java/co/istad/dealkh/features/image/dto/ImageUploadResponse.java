package co.istad.dealkh.features.image.dto;

import lombok.Builder;
import lombok.Data;

/**
 * ImageUploadResponse is a response object for uploading an image.
 * It contains the download URL of the image, the file type, and the size of the image.
 */
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
