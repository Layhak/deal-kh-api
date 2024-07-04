package co.istad.dealkh.features.productrating;

import co.istad.dealkh.domain.Product;
import co.istad.dealkh.domain.ProductRating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductRatingRepository extends JpaRepository<ProductRating, Long> {

    Optional<ProductRating> findByUserIdAndProductId(Long userId, Long productId);

    Optional<ProductRating> findByUserUsernameAndProductSlug(String username, String productSlug);

    ProductRating findProductRatingById(Long id);

//    @Query("select sum(pr.ratingValue) from ProductRating pr where pr.product.id = :id")
//    Double findRatingValueByProductId(@Param("id") Long id);

    @Query("SELECT COALESCE(SUM(pr.ratingValue), 0) FROM ProductRating pr WHERE pr.product.id = :id")
    Double findRatingValueByProductId(@Param("id") Long id);

    @Query("SELECT COUNT(pr) FROM ProductRating pr WHERE pr.product.id = :id")
    Long countByProductId(@Param("id") Long id);

    List<ProductRating> findByProduct(Product product);
}
