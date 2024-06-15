package co.istad.dealkh.features.discount;

import co.istad.dealkh.domain.Discount;
import co.istad.dealkh.domain.DiscountType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface DiscountRepository extends JpaRepository<Discount, Long>, JpaSpecificationExecutor<Discount> {
    boolean existsByDiscountValue(BigDecimal value);

    boolean existsByDiscountValueAndDiscountTypeId(BigDecimal value, Long discountTypeId);
//    Optional<Discount> findByName(String discountName);

//    boolean existsByName(String name);

//    boolean existsByDiscountPercentage(Double DiscountPercentage);


    List<Discount> findByDiscountType(DiscountType discountType);


    List<Discount> findByCreatedBy(String username);
}
