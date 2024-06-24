package co.istad.dealkh.features.productscrape;

import co.istad.dealkh.features.productscrape.dto.ProductScrapeRequest;
import co.istad.dealkh.features.productscrape.dto.ProductScrapeResponse;
import co.istad.dealkh.paging.PageResponse;

import java.util.List;

public interface ProductScrapeService {
    PageResponse<ProductScrapeResponse> getProductScrapes(int page, int size, String field, String order);
    ProductScrapeResponse  postProductScrape(ProductScrapeRequest productScrapeRequest);

}
