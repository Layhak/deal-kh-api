package co.istad.dealkh.features.product.dto;

import co.istad.dealkh.domain.json.Image;
import co.istad.dealkh.features.discount.dto.DiscountResponse;

import java.net.CacheRequest;
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
