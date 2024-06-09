package co.istad.dealkh.features.product;

import co.istad.dealkh.features.product.dto.ProductCreateRequest;
import co.istad.dealkh.features.product.dto.ProductResponse;
import co.istad.dealkh.features.product.dto.ProductUpdateRequest;
import co.istad.dealkh.paging.PageResponse;

import java.util.Map;
import java.util.Optional;

/**
 * ProductService is a service class that provides methods for creating, retrieving, updating, and deleting products.
 * It handles the business logic for managing products in the application.
 */
public interface ProductService {

    /**
     * Creates a new product based on the provided request.
     *
     * @param productCreateRequest the request containing the details for the new product
     * @return a {@link ProductResponse} containing the details of the created product
     */
    ProductResponse createProduct(ProductCreateRequest productCreateRequest);

    Optional<ProductResponse> getProductById(Long id);

    PageResponse<ProductResponse> getAllProducts(int page, int size, String field, String order, Map<String, String> params);

    ProductResponse updateProductById(Long id, ProductUpdateRequest productUpdateRequest);

    void deleteProduct(Long id);

    Double getProductRatingAvg(Long id);

}
