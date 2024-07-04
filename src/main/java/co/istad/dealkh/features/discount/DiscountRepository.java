package co.istad.dealkh.features.discount;

import co.istad.dealkh.domain.Discount;
import co.istad.dealkh.domain.DiscountType;
import co.istad.dealkh.domain.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface DiscountRepository extends JpaRepository<Discount, Long>, JpaSpecificationExecutor<Discount> {
    boolean existsByDiscountValue(BigDecimal value);

    boolean existsByDiscountValueAndDiscountTypeSlugAndShopSlug(BigDecimal value, String discountTypeSlug, String shopSlug);
//    Optional<Discount> findByName(String discountName);

//    boolean existsByName(String name);

//    boolean existsByDiscountPercentage(Double DiscountPercentage);


    List<Discount> findByDiscountType(DiscountType discountType);


    List<Discount> findByCreatedByAndUuid(String username, String uuid);

    Optional<Discount> findByShopSlugAndUuid(String slug, String uuid);

    Optional<Discount> findByUuid(String uuid);

    List<Discount> findAllByCreatedBy(String username);

    List<Double> findAllDiscountValueByDiscountTypeSlugAndShopSlug(String slug, String shopSlug);

    List<Discount> findByShop(Shop shop);
}
