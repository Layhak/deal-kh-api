package co.istad.dealkh.features.product.dto;


import co.istad.dealkh.features.image.dto.ImageRequest;
import co.istad.dealkh.features.image.dto.ImageResponse;
import co.istad.dealkh.validator.name.ValidName;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.util.List;

/**
 * ProductUpdateRequest is a request object for updating a product.
 * It contains the name, price, discount price, description, images, and shop ID.
 *
 * <p>This class uses the following annotations:
 * <ul>
 * <li>{@link NotBlank} - Indicates that the name is required.</li>
 * <li>{@link NotNull} - Indicates that the price is required.</li>
 * </ul>
 * </p>
 */
public record ProductUpdateRequest(

        @NotBlank(message = "Name is required")
        String name,

        @NotNull(message = "Price is required")
        @Positive(message = "Price must be a positive number")
        double price,

        @Size(max = 500, message = "Description must be less than 500 characters")
        String description,
        String shopSlug,
        String discountUuid,
        String categorySlug,
        List<ImageRequest> images


) {
}
