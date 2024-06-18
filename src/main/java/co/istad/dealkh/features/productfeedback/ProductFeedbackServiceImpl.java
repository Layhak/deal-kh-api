package co.istad.dealkh.features.productfeedback;

import co.istad.dealkh.domain.Product;
import co.istad.dealkh.domain.ProductFeedback;
import co.istad.dealkh.domain.User;
import co.istad.dealkh.features.product.ProductRepository;
import co.istad.dealkh.features.productfeedback.dto.ProductFeedbackRequest;
import co.istad.dealkh.features.productfeedback.dto.ProductFeedbackResponse;
import co.istad.dealkh.features.productfeedback.dto.ProductFeedbackUpdate;
import co.istad.dealkh.features.user.UserRepository;
import co.istad.dealkh.mapper.ProductFeedbackMapper;
import lombok.RequiredArgsConstructor;
import org.mapstruct.control.MappingControl;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
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
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    /**
     * Retrieves all product feedback for a given product ID.
     *
     * @param productSlug
     * @return
     */
    @Override
    public List<ProductFeedbackResponse> getProductFeedbacks(String productSlug) {
        return productFeedbackRepository.findByProductSlug(productSlug).stream()
                .map(productFeedbackMapper::toProductFeedbackResponse)
                .toList();
    }

    /**
     * Creates a new product feedback for a given product ID.
     * @param username
     * @param productFeedbackRequest
     * @return
     */
    @Override
    public ProductFeedbackResponse createProductFeedback(String username, ProductFeedbackRequest productFeedbackRequest) {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "User not found"));

        Product product = productRepository.findBySlug(productFeedbackRequest.productSlug())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Product not found"));

        ProductFeedback productFeedback = productFeedbackMapper.toProductFeedback(productFeedbackRequest);

        productFeedback.setUser(user);
        productFeedback.setProduct(product);
        return productFeedbackMapper.toProductFeedbackResponse(productFeedbackRepository.save(productFeedback));
    }

    /**
     * Retrieves a product feedback by its ID.
     *
     * @param username
     * @return
     */
    @Override
    public ProductFeedbackResponse updateProductFeedback(String username, String uuid,  ProductFeedbackUpdate productFeedbackUpdate) {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "User not found!"
                ));


        ProductFeedback productFeedback = productFeedbackRepository.findByUuid(uuid)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Product feedback not found!"
                ));


        if(productFeedbackRepository.findByUserUsernameAndUuid(username, uuid).isEmpty()){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You're not this resource owner!");
        }

        productFeedbackMapper.mapProductFeedbackUpdateRequest(productFeedback, productFeedbackUpdate);

        productFeedback.setUpdatedAt(LocalDateTime.now());
        productFeedback.setUpdatedBy(username);
        productFeedback.setUser(user);
        productFeedback.setDescription(productFeedbackUpdate.description());
        return productFeedbackMapper.toProductFeedbackResponse(productFeedbackRepository.save(productFeedback));

    }

    /**
     * Deletes a product feedback by its ID.
     *
     * @param uuid
     */
    @Override
    public void deleteProductFeedback(String username, String uuid) {

        ProductFeedback productFeedback = productFeedbackRepository.findByUuid(uuid)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Product feedback not found!"
                ));

        if(productFeedbackRepository.findByUserUsernameAndUuid(username, uuid).isEmpty()){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You're not this resource owner!");
        }

        productFeedbackRepository.delete(productFeedback);
    }
}
