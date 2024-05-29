package co.istad.dealkh.features.productfeedback;

import co.istad.dealkh.domain.ProductFeedback;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductFeedbackRepository extends JpaRepository<ProductFeedback, Long> {

    List<ProductFeedback> findByProductId(Long productId);

}
