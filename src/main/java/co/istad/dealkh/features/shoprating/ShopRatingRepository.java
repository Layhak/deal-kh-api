package co.istad.dealkh.features.shoprating;

import co.istad.dealkh.domain.Product;
import co.istad.dealkh.domain.ProductRating;
import co.istad.dealkh.domain.Shop;
import co.istad.dealkh.domain.ShopRating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ShopRatingRepository extends JpaRepository<ShopRating, Long> {
    Optional<ShopRating> findByUserUsernameAndShopSlug(String username, String shopSlug);

    Double findRatingValueByShopSlug(String slug);

    Long countByShopSlug(String slug);

    List<ShopRating> findByShop(Shop shop);

    List<ShopRating> findAllByShop(Shop shop);

    Long countByShop(Shop shop);

    @Query("SELECT AVG(sr.ratingValue) FROM ShopRating sr WHERE sr.shop.slug = :shopSlug")
    Double calculateAverageRatingByShopSlug(@Param("shopSlug") String shopSlug);

}
