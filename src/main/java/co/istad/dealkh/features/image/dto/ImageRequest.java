package co.istad.dealkh.features.image.dto;

/**
 * ImageRequest is a request object for uploading an image.
 * It contains the URL of the image to upload.
 *
 * @param url
 */
public record ImageRequest(
        String url
) {
}
