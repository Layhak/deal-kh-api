package co.istad.dealkh.features.banner;

import co.istad.dealkh.domain.Banner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BannerRepository extends JpaRepository<Banner, Long> {
    Optional<Banner> findByUuid(String uuid);

    List<Banner> findAllByBannerType(String bannerType);
}
