package co.istad.dealkh.features.productfeedback;

import co.istad.dealkh.domain.ProductFeedback;
import co.istad.dealkh.features.product.ProductRepository;
import co.istad.dealkh.features.productfeedback.dto.ProductFeedbackRequest;
import co.istad.dealkh.features.productfeedback.dto.ProductFeedbackResponse;
import co.istad.dealkh.features.user.UserRepository;
import co.istad.dealkh.mapper.ProductFeedbackMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductFeedbackServiceImpl implements ProductFeedbackService {
    private final ProductFeedbackRepository productFeedbackRepository;
    private final ProductFeedbackMapper productFeedbackMapper;

    @Override
    public List<ProductFeedbackResponse> getProductFeedbacks(Long productId) {
        return productFeedbackRepository.findByProductId(productId).stream()
                .map(productFeedbackMapper::toProductFeedbackResponse)
                .toList();
    }

    @Override
    public ProductFeedbackResponse createProductFeedback(ProductFeedbackRequest productFeedbackRequest) {
        ProductFeedback productFeedback = productFeedbackMapper.toProductFeedback(productFeedbackRequest);
        return productFeedbackMapper.toProductFeedbackResponse(productFeedbackRepository.save(productFeedback));
    }

    @Override
    public ProductFeedbackResponse updateProductFeedback(Long id, ProductFeedbackRequest productFeedbackRequest) {
        var productFeedback = productFeedbackRepository.findById(id).orElseThrow();
        productFeedback.setDescription(productFeedbackRequest.description());
        return productFeedbackMapper.toProductFeedbackResponse(productFeedbackRepository.save(productFeedback));

    }

    @Override
    public void deleteProductFeedback(Long id) {
        var productFeedback = productFeedbackRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Product feedback not found")
        );
        productFeedbackRepository.delete(productFeedback);
    }
}
