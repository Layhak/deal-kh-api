package co.istad.dealkh.features.category.dto;

import java.time.LocalDate;

public record CategoryUpdateRequest(
        String name,
        String icon,
        String createdBy,
        LocalDate createdAt,
        LocalDate updateBy,
        LocalDate updatedAt

) {
}
