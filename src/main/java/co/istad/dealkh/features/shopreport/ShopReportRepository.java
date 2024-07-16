package co.istad.dealkh.features.shopreport;

import co.istad.dealkh.domain.Shop;
import co.istad.dealkh.domain.ShopReport;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ShopReportRepository extends JpaRepository<ShopReport, Long> {

    List<ShopReport> findByShopSlug(String slug);

    Optional<ShopReport> findByUserUsernameAndUuid(String username, String uuid);

    Optional<ShopReport> findByUuid(String uuid);

    Optional<ShopReport> findByUserUsername(String username);

    Optional<ShopReport> findByUserUsernameAndShopSlug(String username, String slug);

    List<ShopReport> findByShop(Shop shop);
}
