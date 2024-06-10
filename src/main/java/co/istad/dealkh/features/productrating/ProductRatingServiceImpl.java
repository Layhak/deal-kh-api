package co.istad.dealkh.features.productrating;

import co.istad.dealkh.domain.Product;
import co.istad.dealkh.domain.ProductRating;
import co.istad.dealkh.domain.User;
import co.istad.dealkh.features.product.ProductRepository;
import co.istad.dealkh.features.product.ProductService;
import co.istad.dealkh.features.productrating.dto.ProductRatingRequest;
import co.istad.dealkh.features.productrating.dto.ProductRatingResponse;
import co.istad.dealkh.features.user.UserRepository;
import co.istad.dealkh.mapper.ProductRatingMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

/**
 * ProductRatingServiceImpl is a service implementation of {@link ProductRatingService} that handles product ratings-related operations.
 * It includes methods for creating, retrieving, updating, and deleting product ratings.
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
public class ProductRatingServiceImpl implements ProductRatingService {

    private final ProductRatingRepository productRatingRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final ProductRatingMapper productRatingMapper;
    private final ProductService productService;

    /**
     * Rates a product based on the provided request.
     *
     * @param productRatingRequest
     * @return
     */
    @Override
    public ProductRatingResponse rateProduct(ProductRatingRequest productRatingRequest) {

        ProductRating newProductRating = productRatingMapper.mapProductRatingRequestToProductRating(productRatingRequest);

        User user = userRepository.findById(productRatingRequest.userId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "User not found!"
                ));

        Product product = productRepository.findById(productRatingRequest.productId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Product not found!"
                ));

        if (productRatingRequest.ratingValue() < 0 || productRatingRequest.ratingValue() > 5) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Rating must be between 0 and 5!"
            );
        }

        if (productRatingRepository.findByUserIdAndProductId(productRatingRequest.userId(), productRatingRequest.productId()).isPresent()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "You have already rated this product!"
            );
        }

        newProductRating.setUser(user);
        newProductRating.setProduct(product);
        newProductRating.setCreatedAt(LocalDateTime.now());
        productRatingRepository.save(newProductRating);

        product.setRatingAvg(productService.getProductRatingAvg(product.getId()));
        productRepository.save(product);

        return productRatingMapper.mapProductRatingToProductRatingResponse(newProductRating);
    }

    /**
     * Retrieves all product ratings.
     *
     * @return
     */
    @Override
    public List<ProductRatingResponse> getAllProductRating() {

        return productRatingRepository.findAll()
                .stream()
                .map(productRatingMapper::mapProductRatingToProductRatingResponse)
                .toList();
    }
}
