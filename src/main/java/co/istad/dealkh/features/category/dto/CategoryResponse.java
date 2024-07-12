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
 * @param updatedBy
 * @param updatedAt
 */
public record CategoryResponse(
        String name,
        String slug,
        String icon,
        String banner,
        String createdBy,
        LocalDate createdAt,
        String updatedBy,
        LocalDate updatedAt
) {
}
