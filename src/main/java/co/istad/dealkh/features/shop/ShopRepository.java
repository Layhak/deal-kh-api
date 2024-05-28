package co.istad.dealkh.feature.shop;

import co.istad.dealkh.domain.Shop;

import co.istad.dealkh.domain.ShopType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShopRepository extends JpaRepository<Shop, Long> {

    boolean existsByName(String name);

    boolean existsByEmail(String email);

    List<Shop> findByShopType(ShopType shopType1);

    List<Shop> findByName(String name);
}
