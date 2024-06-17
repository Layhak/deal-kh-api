package co.istad.dealkh.features.product.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

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
        @Size(max = 100, message = "Name must be less than 100 characters")
        String name,

        @NotNull(message = "Price is required")
        double price,

        @Size(max = 500, message = "Description must be less than 500 characters")
        String description,
        String shopSlug,
        String discountUuid,
        String categorySlug

) {
}
