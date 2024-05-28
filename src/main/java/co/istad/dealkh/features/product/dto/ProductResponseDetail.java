package co.istad.dealkh.features.product.dto;

import co.istad.dealkh.domain.Category;
import co.istad.dealkh.domain.Discount;
import co.istad.dealkh.domain.Order;
import co.istad.dealkh.domain.Shop;
import co.istad.dealkh.domain.json.Image;

import java.net.CacheRequest;
import java.time.LocalDate;
import java.util.List;

public record ProductResponseDetail(

        Long id,
        String name,
        double price,
        String description,
        List<Image> images,
        Shop shop,
        Discount discount,
        Category category,
        LocalDate createdAt,
        LocalDate updatedAt,
        String createdBy,
        String updateBy

) {
}
