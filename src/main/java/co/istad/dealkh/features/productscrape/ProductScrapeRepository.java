package co.istad.dealkh.features.productscrape;


import co.istad.dealkh.domain.ProductScrape;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductScrapeRepository extends JpaRepository<ProductScrape, Long> {
}
