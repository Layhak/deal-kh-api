package co.istad.dealkh.features.productscrape.web;

import co.istad.dealkh.base.BaseResponse;
import co.istad.dealkh.features.product.dto.ProductResponse;
import co.istad.dealkh.features.productscrape.ProductScrapeService;
import co.istad.dealkh.features.productscrape.dto.ProductScrapeRequest;
import co.istad.dealkh.features.productscrape.dto.ProductScrapeResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/product-scrape")
@RequiredArgsConstructor
public class ProductScrapeController    {
    private final ProductScrapeService productScrapeService;

        @GetMapping
        @Operation(summary = "Get all product scrapes")
        public BaseResponse<List<ProductScrapeResponse>> getProductScrapes() {
            return BaseResponse.<List<ProductScrapeResponse>>ok("Success").setPayload(productScrapeService.getProductScrapes());
        }

        @PostMapping
        @Operation(summary = "Post product scrape")
        public BaseResponse<ProductScrapeResponse> postProductScrape(@RequestBody ProductScrapeRequest productScrapeRequest) {
            return BaseResponse.<ProductScrapeResponse>createSuccess("Created new product scrape").setPayload(productScrapeService.postProductScrape(productScrapeRequest));
        }

}
