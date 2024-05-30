package co.istad.dealkh.features.discount;

import co.istad.dealkh.domain.Discount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface DiscountRepository extends JpaRepository<Discount, Long>, JpaSpecificationExecutor<Discount> {
    boolean existsByDiscountPercentage(double v);

//    Optional<Discount> findByName(String discountName);

//    boolean existsByName(String name);

//    boolean existsByDiscountPercentage(Double DiscountPercentage);
}
