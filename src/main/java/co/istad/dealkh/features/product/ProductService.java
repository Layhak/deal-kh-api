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

    /**
     * Retrieves a product by its ID.
     *
     * @param slug
     * @return
     */
    Optional<ProductResponse> getProductBySlug(String slug);

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
    PageResponse<ProductResponse> getAllProducts(int page, int size, String field, String order, Map<String, String> params);

    /**
     * Updates a product based on its ID.
     * @param username
     * @param slug
     * @param productUpdateRequest
     * @return
     */
    ProductResponse updateProductBySlug(String username, String slug, ProductUpdateRequest productUpdateRequest);

    /**
     * Deletes a product based on its ID.
     *
     * @param username
     * @param slug
     * @return
     */
    void deleteProduct(String username, String slug);

    /**
     * Retrieves a product rating average based on its ID.
     *
     * @param id
     * @return
     */
    Double getProductRatingAvg(Long id);

    PageResponse<ProductResponse> getProductShopOwner(int page, int size, String field, String order, String slug);


    /**
     * Retrieves all products.
     * This method uses the {@link PageResponse} class to return a paginated list of products.
     *
     * @param username
     * @param page
     * @param size
     * @param field
     * @param order
     * @param params
     * @return
     */
    PageResponse<ProductResponse> getAllProductByShopOwner(String username, int page, int size, String field, String order, Map<String, String> params);

}
