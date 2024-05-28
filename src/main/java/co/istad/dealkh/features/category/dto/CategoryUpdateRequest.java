package co.istad.dealkh.features.category.dto;

import java.time.LocalDate;

public record CategoryUpdateRequest(

        String name,
        String icon

) {
}
