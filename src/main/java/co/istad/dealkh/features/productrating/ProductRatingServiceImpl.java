package co.istad.dealkh.features.productrating;

import co.istad.dealkh.domain.Product;
import co.istad.dealkh.domain.ProductRating;
import co.istad.dealkh.domain.User;
import co.istad.dealkh.features.product.ProductRepository;
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
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductRatingServiceImpl implements ProductRatingService{

    private final ProductRatingRepository productRatingRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final ProductRatingMapper productRatingMapper;

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

        return productRatingMapper.mapProductRatingToProductRatingResponse(newProductRating);
    }

    @Override
    public List<ProductRatingResponse> getAllProductRating() {

        return productRatingRepository.findAll()
                .stream()
                .map(productRatingMapper::mapProductRatingToProductRatingResponse)
                .toList();
    }
}
