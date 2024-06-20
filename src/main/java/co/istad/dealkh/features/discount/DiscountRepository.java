package co.istad.dealkh.features.discount;

import co.istad.dealkh.domain.Discount;
import co.istad.dealkh.domain.DiscountType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface DiscountRepository extends JpaRepository<Discount, Long>, JpaSpecificationExecutor<Discount> {
    boolean existsByDiscountValue(BigDecimal value);

    boolean existsByDiscountValueAndDiscountTypeSlug(BigDecimal value, String discountTypeSlug);
//    Optional<Discount> findByName(String discountName);

//    boolean existsByName(String name);

//    boolean existsByDiscountPercentage(Double DiscountPercentage);


    List<Discount> findByDiscountType(DiscountType discountType);


    List<Discount> findByCreatedByAndUuid(String username, String uuid);

    Optional<Discount> findByUuid(String uuid);
}
