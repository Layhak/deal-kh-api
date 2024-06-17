package co.istad.dealkh.features.category.dto;

import java.time.LocalDate;

/**
 * CategoryResponse is a response object for a category.
 * It contains the ID, name, icon, and created by fields.
 *
 * @param name
 * @param icon
 * @param createdBy
 * @param createdAt
 * @param updateBy
 * @param updatedAt
 */
public record CategoryResponse(
        String name,
        String slug,
        String icon,
        String createdBy,
        LocalDate createdAt,
        LocalDate updateBy,
        LocalDate updatedAt
) {
}
