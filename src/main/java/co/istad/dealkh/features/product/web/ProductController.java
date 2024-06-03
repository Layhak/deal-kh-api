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
    PageResponse<ProductResponse> filterProduct(@RequestParam Map<String, String> params) {
        return productService.filterProduct(params);
    }

    @PutMapping("/{id}")
    BaseResponse<ProductResponse> updateProductById(@PathVariable Long id, @RequestBody ProductUpdateRequest productUpdateRequest) {
        return BaseResponse.<ProductResponse>updateSuccess("Update product successfully!")
                .setPayload(productService.updateProductById(id, productUpdateRequest));
    }

    @DeleteMapping("/{id}")
    BaseResponse<?> deleteProductById(@PathVariable Long id) {
        productService.deleteProduct(id);
        return BaseResponse.deleteSuccess("Delete product successfully!");
    }

}
