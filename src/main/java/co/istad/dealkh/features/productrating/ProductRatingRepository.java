package co.istad.dealkh.features.productrating;

import co.istad.dealkh.domain.ProductRating;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRatingRepository extends JpaRepository<ProductRating, Long> {

    Optional<ProductRating> findByUserIdAndProductId(Long userId, Long productId);
}
