package co.istad.dealkh.features.productrating;

import co.istad.dealkh.domain.ProductRating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ProductRatingRepository extends JpaRepository<ProductRating, Long> {

    Optional<ProductRating> findByUserIdAndProductId(Long userId, Long productId);

    @Query("SELECT SUM(pr.ratingValue) FROM ProductRating pr WHERE pr.product.id = :id")
    Double findRatingByProductId(@Param("id") Long id);

    Long countByProductId(Long id);
}
