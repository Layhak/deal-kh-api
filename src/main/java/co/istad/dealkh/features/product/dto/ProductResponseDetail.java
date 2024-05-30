package co.istad.dealkh.features.product.dto;

import co.istad.dealkh.domain.json.Image;

import java.time.LocalDate;
import java.util.List;

public record ProductResponseDetail(

        Long id,
        String name,
        double price,
        String description,
        List<Image> images,
        String shop,
        Double discountPercentage,
        String category,
        LocalDate createdAt,
        LocalDate updatedAt,
        String createdBy,
        String updateBy

) {
}
