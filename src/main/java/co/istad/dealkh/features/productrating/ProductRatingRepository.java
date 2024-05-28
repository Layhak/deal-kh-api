package co.istad.dealkh.features.productrating;

import co.istad.dealkh.domain.ProductRating;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRatingRepository extends JpaRepository<ProductRating, Long> {
}
