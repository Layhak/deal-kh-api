package co.istad.dealkh.features.shop;

import co.istad.dealkh.domain.Shop;

import co.istad.dealkh.domain.ShopType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ShopRepository extends JpaRepository<Shop, Long> {

    boolean existsByName(String name);

    boolean existsBySlug(String slug);

    boolean existsByEmail(String email);

    boolean existsByPhoneNumber(String phoneNumber);

    List<Shop> findByShopType(ShopType shopType1);

    List<Shop> findAllByName(String name);

    Optional<Shop> findByName(String name);

    Optional<Shop> findBySlug(String slug);
}
