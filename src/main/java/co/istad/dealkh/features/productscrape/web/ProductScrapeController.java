package co.istad.dealkh.features.productscrape.web;

import co.istad.dealkh.base.BaseResponse;
import co.istad.dealkh.features.productscrape.ProductScrapeService;
import co.istad.dealkh.features.productscrape.dto.ProductScrapeRequest;
import co.istad.dealkh.features.productscrape.dto.ProductScrapeResponse;
import co.istad.dealkh.paging.PageResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/product-scrape")
@RequiredArgsConstructor
public class ProductScrapeController {
    private final ProductScrapeService productScrapeService;

    @GetMapping
    @Operation(summary = "Get all product scrapes")
    public BaseResponse<PageResponse<ProductScrapeResponse>> getProductScrapes(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "25") int size,
            @RequestParam(defaultValue = "name") String field,
            @RequestParam(defaultValue = "asc") String order) {
        return BaseResponse.<PageResponse<ProductScrapeResponse>>ok("Success").setPayload(productScrapeService.getProductScrapes(page, size, field, order));
    }

    @PostMapping
    @Operation(summary = "Post product scrape")
    public BaseResponse<ProductScrapeResponse> postProductScrape(@RequestBody ProductScrapeRequest productScrapeRequest) {
        return BaseResponse.<ProductScrapeResponse>createSuccess("Created new product scrape").setPayload(productScrapeService.postProductScrape(productScrapeRequest));
    }

}
