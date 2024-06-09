package co.istad.dealkh.features.category.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * CategoryCreateRequest is a request object for creating a new category.
 * It contains the name and icon of the category.
 *
 * <p>This class uses the following annotations:
 * <ul>
 * <li>{@link NotBlank} - Indicates that the name and icon are required.</li>
 * </ul>
 * </p>
 */
public record CategoryCreateRequest(

        @NotBlank(message = "Name is required")
        String name,

        String icon
) {
}
