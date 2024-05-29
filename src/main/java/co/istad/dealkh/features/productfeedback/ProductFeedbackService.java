package co.istad.dealkh.features.productfeedback;

import co.istad.dealkh.features.productfeedback.dto.ProductFeedbackRequest;
import co.istad.dealkh.features.productfeedback.dto.ProductFeedbackResponse;

import java.util.List;

public interface ProductFeedbackService {
    List<ProductFeedbackResponse> getProductFeedbacks(Long productId);
    ProductFeedbackResponse createProductFeedback(ProductFeedbackRequest productFeedbackRequest);
   ProductFeedbackResponse updateProductFeedback(Long id, ProductFeedbackRequest productFeedbackRequest);
    void deleteProductFeedback(Long id);

}
