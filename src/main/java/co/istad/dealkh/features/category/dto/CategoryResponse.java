package co.istad.dealkh.features.category.dto;

import java.time.LocalDate;

public record CategoryResponse(
        Long id,
        String name,
        String icon,
        String createdBy,
        LocalDate createdAt,
        LocalDate updateBy,
        LocalDate updatedAt
) {
}
