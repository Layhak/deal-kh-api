package co.istad.dealkh.features.productscrape;

import co.istad.dealkh.features.productscrape.dto.ProductScrapeRequest;
import co.istad.dealkh.features.productscrape.dto.ProductScrapeResponse;

import java.util.List;

public interface ProductScrapeService {
    List<ProductScrapeResponse> getProductScrapes();
    ProductScrapeResponse  postProductScrape(ProductScrapeRequest productScrapeRequest);

}
