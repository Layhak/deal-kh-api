package co.istad.dealkh.features.productscrape.dto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.math.BigDecimal;

public record ProductScrapeRequest(
        String name,
        String description,
        double price,

        String image,
        BigDecimal discountPercentage,
        double rating,
        String url
) {
}
