package co.istad.dealkh.features.discounttype;

import co.istad.dealkh.domain.DiscountType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiscountTypeRepository extends JpaRepository<DiscountType, Long> {
}