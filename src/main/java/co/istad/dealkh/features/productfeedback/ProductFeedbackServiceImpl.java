package co.istad.dealkh.features.productfeedback;

import co.istad.dealkh.domain.ProductFeedback;
import co.istad.dealkh.features.productfeedback.dto.ProductFeedbackRequest;
import co.istad.dealkh.features.productfeedback.dto.ProductFeedbackResponse;
import co.istad.dealkh.mapper.ProductFeedbackMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * ProductFeedbackServiceImpl is a service implementation of {@link ProductFeedbackService} that handles product feedback-related operations.
 * It includes methods for creating, retrieving, updating, and deleting product feedback.
 *
 * <p>This class uses the following annotations:
 * <ul>
 * <li>{@link Service} - Indicates that this class is a Spring service.</li>
 * <li>{@link RequiredArgsConstructor} - Generates a constructor with required arguments (final fields).</li>
 * </ul>
 * </p>
 */
@Service
@RequiredArgsConstructor
public class ProductFeedbackServiceImpl implements ProductFeedbackService {
    private final ProductFeedbackRepository productFeedbackRepository;
    private final ProductFeedbackMapper productFeedbackMapper;

    /**
     * Retrieves all product feedback for a given product ID.
     *
     * @param productId
     * @return
     */
    @Override
    public List<ProductFeedbackResponse> getProductFeedbacks(Long productId) {
        return productFeedbackRepository.findByProductId(productId).stream()
                .map(productFeedbackMapper::toProductFeedbackResponse)
                .toList();
    }

    /**
     * Creates a new product feedback for a given product ID.
     *
     * @param productFeedbackRequest
     * @return
     */
    @Override
    public ProductFeedbackResponse createProductFeedback(ProductFeedbackRequest productFeedbackRequest) {
        ProductFeedback productFeedback = productFeedbackMapper.toProductFeedback(productFeedbackRequest);
        return productFeedbackMapper.toProductFeedbackResponse(productFeedbackRepository.save(productFeedback));
    }

    /**
     * Retrieves a product feedback by its ID.
     *
     * @param id
     * @return
     */
    @Override
    public ProductFeedbackResponse updateProductFeedback(Long id, ProductFeedbackRequest productFeedbackRequest) {
        var productFeedback = productFeedbackRepository.findById(id).orElseThrow();
        productFeedback.setDescription(productFeedbackRequest.description());
        return productFeedbackMapper.toProductFeedbackResponse(productFeedbackRepository.save(productFeedback));

    }

    /**
     * Deletes a product feedback by its ID.
     *
     * @param id
     */
    @Override
    public void deleteProductFeedback(Long id) {
        var productFeedback = productFeedbackRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Product feedback not found")
        );
        productFeedbackRepository.delete(productFeedback);
    }
}
