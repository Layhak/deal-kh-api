package co.istad.dealkh.mapper;

import co.istad.dealkh.domain.ProductScrape;
import co.istad.dealkh.features.productscrape.dto.ProductScrapeRequest;
import co.istad.dealkh.features.productscrape.dto.ProductScrapeResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductScrapeMapper {
    ProductScrapeResponse toProductScrapeResponse(ProductScrape productScrape);
    ProductScrape toProductScrape(ProductScrapeRequest productScrapeRequest);
}
