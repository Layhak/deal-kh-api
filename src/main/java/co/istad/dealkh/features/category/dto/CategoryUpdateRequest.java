package co.istad.dealkh.features.category.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * CategoryUpdateRequest is a request object for updating a category.
 * It contains the name and icon of the category.
 *
 * <p>This class uses the following annotations:
 * <ul>
 * <li>{@link NotBlank} - Indicates that the name and icon are required.</li>
 * </ul>
 * </p>
 */
public record CategoryUpdateRequest(

        @NotBlank(message = "Name is required")
        String name,

        String icon,

        String banner

) {
}
