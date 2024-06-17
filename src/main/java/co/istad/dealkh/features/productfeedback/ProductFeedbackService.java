package co.istad.dealkh.features.productfeedback;

import co.istad.dealkh.features.productfeedback.dto.ProductFeedbackRequest;
import co.istad.dealkh.features.productfeedback.dto.ProductFeedbackResponse;
import co.istad.dealkh.features.productfeedback.dto.ProductFeedbackUpdate;

import java.util.List;

/**
 * ProductFeedbackService is an interface for managing product feedback.
 * It includes methods for creating, retrieving, updating, and deleting product feedback.
 */
public interface ProductFeedbackService {
    /**
     * Retrieves all product feedback for a given product ID.
     *
     * @param productSlug
     * @return
     */
    List<ProductFeedbackResponse> getProductFeedbacks(String productSlug);

    /**
     * Creates a new product feedback for a given product ID.
     *
     * @param productFeedbackRequest
     * @return
     */
    ProductFeedbackResponse createProductFeedback(String username,ProductFeedbackRequest productFeedbackRequest);

    /**
     * Retrieves a product feedback by its ID.
     *
     * @param username
     * @param productSlug
     * @return
     */
    ProductFeedbackResponse updateProductFeedback(String username,String productSlug, ProductFeedbackUpdate productFeedbackUpdate);

    /**
     * Deletes a product feedback by its ID.
     *
     * @param uuid
     * @return
     */
    void deleteProductFeedback(String username, String uuid);

}
