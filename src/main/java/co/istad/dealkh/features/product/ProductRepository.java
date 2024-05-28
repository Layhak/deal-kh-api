package co.istad.dealkh.features.product;

import co.istad.dealkh.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
