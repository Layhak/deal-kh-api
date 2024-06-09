package co.istad.dealkh.features.productfeedback;

import co.istad.dealkh.features.productfeedback.dto.ProductFeedbackRequest;
import co.istad.dealkh.features.productfeedback.dto.ProductFeedbackResponse;

import java.util.List;

/**
 * ProductFeedbackService is an interface for managing product feedback.
 * It includes methods for creating, retrieving, updating, and deleting product feedback.
 */
public interface ProductFeedbackService {
    /**
     * Retrieves all product feedback for a given product ID.
     *
     * @param productId
     * @return
     */
    List<ProductFeedbackResponse> getProductFeedbacks(Long productId);

    /**
     * Creates a new product feedback for a given product ID.
     *
     * @param productFeedbackRequest
     * @return
     */
    ProductFeedbackResponse createProductFeedback(ProductFeedbackRequest productFeedbackRequest);

    /**
     * Retrieves a product feedback by its ID.
     *
     * @param id
     * @return
     */
    ProductFeedbackResponse updateProductFeedback(Long id, ProductFeedbackRequest productFeedbackRequest);

    /**
     * Deletes a product feedback by its ID.
     *
     * @param id
     * @return
     */
    void deleteProductFeedback(Long id);

}
