package co.istad.dealkh.features.discounttype;

import co.istad.dealkh.domain.DiscountType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DiscountTypeRepository extends JpaRepository<DiscountType, Long>{
    boolean existsByName(String name);

    boolean existsBySlug(String slug);

    Optional<DiscountType> findByName(String name);

    Optional<DiscountType> findBySlug(String slug);
}