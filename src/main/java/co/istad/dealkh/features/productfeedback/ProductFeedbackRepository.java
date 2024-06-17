package co.istad.dealkh.features.productfeedback;

import co.istad.dealkh.domain.ProductFeedback;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;


public interface ProductFeedbackRepository extends JpaRepository<ProductFeedback, Long> {

    List<ProductFeedback> findByProductSlug(String productSlug);

    Optional<ProductFeedback> findByUserUsernameAndUuid(String username, String uuid);

    Optional<ProductFeedback> findByUuid(String uuid);

    Optional<ProductFeedback> findByUserUsername(String username);
}
