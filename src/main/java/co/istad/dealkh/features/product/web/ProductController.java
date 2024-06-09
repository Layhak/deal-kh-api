package co.istad.dealkh.features.product.web;

import co.istad.dealkh.base.BaseResponse;
import co.istad.dealkh.features.product.ProductService;
import co.istad.dealkh.features.product.dto.ProductCreateRequest;
import co.istad.dealkh.features.product.dto.ProductResponse;
import co.istad.dealkh.features.product.dto.ProductUpdateRequest;
import co.istad.dealkh.paging.PageResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService productService;

    @PostMapping("")
    BaseResponse<ProductResponse> createProduct(@RequestBody @Valid ProductCreateRequest productCreateRequest) {
        return BaseResponse.<ProductResponse>createSuccess("Successfully created product!")
                .setPayload(productService.createProduct(productCreateRequest));
    }

    @GetMapping("/{id}")
    BaseResponse<Optional<ProductResponse>> getProductById(@PathVariable Long id) {
        return BaseResponse.<Optional<ProductResponse>>ok("Successfully retrieved product!")
                .setPayload(productService.getProductById(id));
    }

    @GetMapping("")
    BaseResponse<PageResponse<ProductResponse>> filterProduct(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "2") int size,
            @RequestParam(defaultValue = "name") String field,
            @RequestParam(defaultValue = "asc") String order,
            @RequestParam Map<String, String> params
    ) {
        return BaseResponse.<PageResponse<ProductResponse>>ok("Successfully retrieved products!")
                .setPayload(productService.getAllProducts(page, size, field, order, params));
    }

    @PutMapping("/{id}")
    BaseResponse<ProductResponse> updateProductById(@PathVariable Long id, @RequestBody ProductUpdateRequest productUpdateRequest) {
        return BaseResponse.<ProductResponse>ok("Update product successfully!")
                .setPayload(productService.updateProductById(id, productUpdateRequest));
    }

    @DeleteMapping("/{id}")
    BaseResponse<?> deleteProductById(@PathVariable Long id) {
        productService.deleteProduct(id);
        return BaseResponse.ok("Delete product successfully!");
    }

}
