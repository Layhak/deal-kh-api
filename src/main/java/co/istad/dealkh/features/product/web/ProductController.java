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

/**
 * ProductController is a controller for managing products.
 * It handles creating, retrieving, updating, and deleting products.
 *
 * <p>This class uses the following annotations:
 * <ul>
 * <li>{@link RestController} - Indicates that this class is a REST controller.</li>
 * <li>{@link RequiredArgsConstructor} - Generates a constructor with required arguments (final fields).</li>
 * <li>{@link RequestMapping} - Maps HTTP requests to handler methods of MVC and REST controllers.</li>
 * </ul>
 * </p>
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService productService;

    /**
     * Creates a new product based on the provided request.
     *
     * @param productCreateRequest
     * @return
     */
    @PostMapping("")
    BaseResponse<ProductResponse> createProduct(@RequestBody @Valid ProductCreateRequest productCreateRequest) {
        return BaseResponse.<ProductResponse>createSuccess("Successfully created product!")
                .setPayload(productService.createProduct(productCreateRequest));
    }

    /**
     * Retrieves a product based on its ID.
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    BaseResponse<Optional<ProductResponse>> getProductById(@PathVariable Long id) {
        return BaseResponse.<Optional<ProductResponse>>ok("Successfully retrieved product!")
                .setPayload(productService.getProductById(id));
    }

    /**
     * Retrieves all products.
     * This method uses the {@link PageResponse} class to return a paginated list of products.
     *
     * @param page
     * @param size
     * @param field
     * @param order
     * @param params
     * @return
     */
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

    /**
     * Updates a product based on its ID.
     *
     * @param id
     * @param productUpdateRequest
     * @return
     */
    @PutMapping("/{id}")
    BaseResponse<ProductResponse> updateProductById(@PathVariable Long id, @RequestBody ProductUpdateRequest productUpdateRequest) {
        return BaseResponse.<ProductResponse>ok("Update product successfully!")
                .setPayload(productService.updateProductById(id, productUpdateRequest));
    }

    /**
     * Deletes a product based on its ID.
     *
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    BaseResponse<?> deleteProductById(@PathVariable Long id) {
        productService.deleteProduct(id);
        return BaseResponse.ok("Delete product successfully!");
    }

}
