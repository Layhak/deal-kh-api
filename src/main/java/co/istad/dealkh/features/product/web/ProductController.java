package co.istad.dealkh.features.product.web;

import co.istad.dealkh.base.BaseResponse;
import co.istad.dealkh.features.product.ProductService;
import co.istad.dealkh.features.product.dto.ProductCreateRequest;
import co.istad.dealkh.features.product.dto.ProductResponse;
import co.istad.dealkh.features.product.dto.ProductUpdateRequest;
import co.istad.dealkh.paging.PageResponse;
import co.istad.dealkh.security.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

import static co.istad.dealkh.base.BaseResponse.ok;

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
    @PostMapping
    BaseResponse<ProductResponse> createProduct(@RequestBody @Valid ProductCreateRequest productCreateRequest) {
        return BaseResponse.<ProductResponse>createSuccess("Successfully created product!")
                .setPayload(productService.createProduct(productCreateRequest));
    }

    /**
     * Retrieves a product based on its Name.
     *
     * @param slug
     * @return
     */
    @GetMapping("/{slug}")
    BaseResponse<Optional<ProductResponse>> getProductBySlug(@PathVariable String slug) {
        return BaseResponse.<Optional<ProductResponse>>ok("Successfully retrieved product!")
                .setPayload(productService.getProductBySlug(slug));
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
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get all products")
    BaseResponse<PageResponse<ProductResponse>> filterProduct(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "25") int size,
            @RequestParam(defaultValue = "name") String field,
            @RequestParam(defaultValue = "asc") String order,
            @RequestParam Map<String, String> params
    ) {
        return BaseResponse.<PageResponse<ProductResponse>>ok("Successfully retrieved products!")
                .setPayload(productService.getAllProducts(page, size, field, order, params));
    }

    /**
     * Updates a product based on its NAME.
     *
     * @param slug
     * @param productUpdateRequest
     * @return
     */
    @PutMapping("/{slug}")
    BaseResponse<ProductResponse> updateProductBySlug(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @PathVariable String slug,
            @RequestBody ProductUpdateRequest productUpdateRequest) {
        return BaseResponse.<ProductResponse>ok("Update product successfully!")
                .setPayload(productService.updateProductBySlug(customUserDetails.getUsername(), slug, productUpdateRequest));
    }

    /**
     * Deletes a product based on its NAME.
     *
     * @param slug
     * @return
     */
    @DeleteMapping("/{slug}")
    BaseResponse<?> deleteProductBySlug(@AuthenticationPrincipal CustomUserDetails customUserDetails, @PathVariable String slug) {
        productService.deleteProduct(customUserDetails.getUsername(), slug);
        return ok("Delete product successfully!")
                .setPayload("No content");
    }

    /**
     * Retrieves all products.
     * This method uses the {@link PageResponse} class to return a paginated list of products.
     *
     * @param page
     * @param size
     * @param field
     * @param order
     * @param slug
     * @return
     */
    @GetMapping("/shop/{slug}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get all products shop owner")
    BaseResponse<PageResponse<ProductResponse>> getProductShopOwner(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "25") int size,
            @RequestParam(defaultValue = "name") String field,
            @RequestParam(defaultValue = "asc") String order,
            @PathVariable String slug
    ) {
        return BaseResponse.<PageResponse<ProductResponse>>ok("Successfully retrieved products!")
                .setPayload(productService.getProductShopOwner(page, size, field, order, slug));
    }

}
