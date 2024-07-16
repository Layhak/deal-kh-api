package co.istad.dealkh.features.productfeedback;

import co.istad.dealkh.domain.Product;
import co.istad.dealkh.domain.ProductFeedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


public interface ProductFeedbackRepository extends JpaRepository<ProductFeedback, Long> {

    List<ProductFeedback> findByProductSlug(String productSlug);

    Optional<ProductFeedback> findByUserUsernameAndUuid(String username, String uuid);

    Optional<ProductFeedback> findByUuid(String uuid);

    Optional<ProductFeedback> findByUserUsername(String username);

    Optional<ProductFeedback> findByUserUsernameAndProductSlug(String username, String productSlug);

    @Modifying
    @Transactional
    @Query("DELETE FROM ProductFeedback pf WHERE pf.product = :product")
    void deleteByProduct(Product product);

    List<ProductFeedback> findByProduct(Product product);
}
