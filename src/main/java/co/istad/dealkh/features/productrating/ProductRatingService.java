package co.istad.dealkh.features.productrating;

import co.istad.dealkh.features.productrating.dto.ProductRatingCount;
import co.istad.dealkh.features.productrating.dto.ProductRatingRequest;
import co.istad.dealkh.features.productrating.dto.ProductRatingResponse;
import co.istad.dealkh.features.productrating.dto.ProductRatingUpdateRequest;

import java.util.List;

/**
 * ProductRatingService is an interface for managing product ratings.
 * It includes methods for creating, retrieving, updating, and deleting product ratings.
 */
public interface ProductRatingService {
    /**
     * Rates a product based on the provided request.
     *
     * @param productRatingRequest
     * @return
     */
    ProductRatingResponse rateProduct(String username, ProductRatingRequest productRatingRequest);

    ProductRatingResponse updateProductRating(String username, String productSlug, ProductRatingUpdateRequest productRatingUpdateRequestRequest);

    /**
     * Retrieves all product ratings.
     *
     * @return
     */
    List<ProductRatingResponse> getAllProductRating();

    List<ProductRatingResponse> getProductRatingByProductSlug(String productSlug);

    ProductRatingCount countByProductSlug(String productSlug);

    void deleteByRating(String username, String productSlug);
}
