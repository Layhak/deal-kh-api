package co.istad.deal_kh.dealkhapi.feature.shop;

import co.istad.deal_kh.dealkhapi.domain.Shop;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShopRepository extends JpaRepository<Shop, Long> {
}
