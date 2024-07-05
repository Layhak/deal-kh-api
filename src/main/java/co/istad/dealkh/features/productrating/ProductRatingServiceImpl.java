package co.istad.dealkh.features.productrating;

import co.istad.dealkh.domain.Product;
import co.istad.dealkh.domain.ProductRating;
import co.istad.dealkh.domain.User;
import co.istad.dealkh.features.product.ProductRepository;
import co.istad.dealkh.features.product.ProductService;
import co.istad.dealkh.features.productrating.dto.ProductRatingRequest;
import co.istad.dealkh.features.productrating.dto.ProductRatingResponse;
import co.istad.dealkh.features.productrating.dto.ProductRatingUpdateRequest;
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
    public ProductRatingResponse rateProduct(String username, ProductRatingRequest productRatingRequest) {

        ProductRating newProductRating = productRatingMapper.mapProductRatingRequestToProductRating(productRatingRequest);

        Product product = productRepository.findBySlug(productRatingRequest.productSlug())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Product not found!"
                ));

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "User not found!"
                ));

        if (productRatingRequest.ratingValue() < 0 || productRatingRequest.ratingValue() > 5) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Rating must be between 0 and 5!"
            );
        }

        if (productRatingRepository.findByUserUsernameAndProductSlug(username, productRatingRequest.productSlug()).isPresent()) {
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

    @Override
    public ProductRatingResponse updateProductRating(String username, String productSlug, ProductRatingUpdateRequest productRatingUpdateRequestRequest) {

        ProductRating productRating = productRatingRepository.findByUserUsernameAndProductSlug(username, productSlug)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Product rating not found!"
                ));

        if (productRatingRepository.findByUserUsernameAndProductSlug(username, productSlug).isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "You're not this resource owner!");
        }

        productRating.setUpdatedAt(LocalDateTime.now());
        productRating.setUpdatedBy(username);
        productRatingMapper.mapProductRatingUpdateRequest(productRating, productRatingUpdateRequestRequest);
        productRatingRepository.save(productRating);

        return productRatingMapper.mapProductRatingToProductRatingResponse(productRating);
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
