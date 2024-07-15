package co.istad.dealkh.features.shopreport;

import co.istad.dealkh.domain.ShopReport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShopReportRepository extends JpaRepository<ShopReport, Long> {
}
