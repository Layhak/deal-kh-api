package co.istad.dealkh.features.category.dto;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

public record CategoryCreateRequest(

        @NotBlank(message = "Name is required")
        String name,

        String icon,
        String createdBy,
        LocalDate createdAt,
        LocalDate updateBy,
        LocalDate updatedAt
) {
}
