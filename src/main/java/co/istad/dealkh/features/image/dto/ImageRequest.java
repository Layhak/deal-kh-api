package co.istad.dealkh.features.image.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * ImageRequest is a request object for uploading an image.
 * It contains the URL of the image to upload.
 *
 * @param url
 */
public record ImageRequest(

        @NotBlank(message = "Url is required")
        String url
) {
}
