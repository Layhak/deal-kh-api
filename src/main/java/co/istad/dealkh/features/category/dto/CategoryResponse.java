package co.istad.dealkh.features.category.dto;

import java.time.LocalDate;

/**
 * CategoryResponse is a response object for a category.
 * It contains the ID, name, icon, and created by fields.
 *
 * @param id
 * @param name
 * @param icon
 * @param createdBy
 * @param createdAt
 * @param updateBy
 * @param updatedAt
 */
public record CategoryResponse(
        Long id,
        String name,
        String slash,
        String icon,
        String createdBy,
        LocalDate createdAt,
        LocalDate updateBy,
        LocalDate updatedAt
) {
}
