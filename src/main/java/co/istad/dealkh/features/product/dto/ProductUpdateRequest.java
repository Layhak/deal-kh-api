package co.istad.dealkh.features.product.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

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
        double price,

        String description,
        Long shopId,
        Long discountId,
        Long categoryId

) {
}
