package co.istad.dealkh.features.shop;

import co.istad.dealkh.domain.Shop;
import co.istad.dealkh.domain.ShopType;
import co.istad.dealkh.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ShopRepository extends JpaRepository<Shop, Long> {

    boolean existsByEmail(String email);

    List<Shop> findByShopType(ShopType shopType1);

    List<Shop> findByName(String name);

    boolean existsBySlug(String slug);

    Optional<Shop> findBySlug(String slug);

    boolean existsByPhoneNumber(String phoneNumber);

    Page<Shop> findByUsersContains(User user, Pageable pageable);

    Optional<Shop> findBySlugAndCreatedBy(String slug, String username);

    Page<Shop> findAllByIsVerified(Boolean isVerified, Pageable pageable);




}
