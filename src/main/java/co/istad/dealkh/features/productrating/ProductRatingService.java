package co.istad.dealkh.features.productrating;

import co.istad.dealkh.features.productrating.dto.ProductRatingRequest;
import co.istad.dealkh.features.productrating.dto.ProductRatingResponse;

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

    /**
     * Retrieves all product ratings.
     *
     * @return
     */
    List<ProductRatingResponse> getAllProductRating();
}
