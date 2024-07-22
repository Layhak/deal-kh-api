package co.istad.dealkh.features.productscrape.web;

import co.istad.dealkh.base.BaseResponse;
import co.istad.dealkh.features.productscrape.ProductScrapeService;
import co.istad.dealkh.features.productscrape.dto.ProductScrapeRequest;
import co.istad.dealkh.features.productscrape.dto.ProductScrapeResponse;
import co.istad.dealkh.paging.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/product-scrape")
@RequiredArgsConstructor
public class ProductScrapeController {
    private final ProductScrapeService productScrapeService;

    @GetMapping
        public BaseResponse<PageResponse<ProductScrapeResponse>> getProductScrapes(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "25") int size,
            @RequestParam(defaultValue = "name") String field,
            @RequestParam(defaultValue = "asc") String order) {
        return BaseResponse.<PageResponse<ProductScrapeResponse>>ok("Get all product scrapes successfully!").setPayload(productScrapeService.getProductScrapes(page, size, field, order));
    }

    @PostMapping
        public BaseResponse<ProductScrapeResponse> postProductScrape(@RequestBody ProductScrapeRequest productScrapeRequest) {
        return BaseResponse.<ProductScrapeResponse>createSuccess("Created new product scrape").setPayload(productScrapeService.postProductScrape(productScrapeRequest));
    }

}
