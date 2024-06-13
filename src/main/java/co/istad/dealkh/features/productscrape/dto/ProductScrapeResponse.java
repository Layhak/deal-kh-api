package co.istad.dealkh.features.productscrape.dto;

import java.math.BigDecimal;

public record ProductScrapeResponse(
        Long id,
        String name,
        String description,
        double price,
        double discountPrice,

        String image,
        BigDecimal discountPercentage,
        double rating,
        String url
) {
}
