package co.istad.dealkh.features.product.dto;

import co.istad.dealkh.features.image.dto.ImageResponse;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.List;

/**
 * ProductCreateRequest is a request object for creating a new product.
 * It contains the name, price, discount price, description, images, and shop ID.
 *
 * <p>This class uses the following annotations:
 * <ul>
 * <li>{@link NotBlank} - Indicates that the name and description are required.</li>
 * <li>{@link NotNull} - Indicates that the price and discount price are required.</li>
 * </ul>
 * </p>
 */
public record ProductCreateRequest(

        @NotBlank(message = "Name is required")
        String name,

        @Positive(message = "Price must be a positive number")
        double price,

        String description,
        List<ImageResponse> images,

        @NotNull(message = "Create shop first before create product")
        String shopSlug,

        String discountUuid,

        @NotNull(message = "Product category is required")
        String categorySlug

) {
}
