package co.istad.dealkh.feature.shop;

import co.istad.dealkh.domain.Shop;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShopRepository extends JpaRepository<Shop, Long> {
}
