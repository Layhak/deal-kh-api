package co.istad.dealkh.features.product.dto;

import co.istad.dealkh.domain.json.Image;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;
import java.util.List;

public record ProductUpdateRequest(

        String name,
        double price,
        String description,
        List<Image> images,
        Long shopId,
        Long discountId,
        Long categoryId

) {
}
