package co.istad.dealkh.features.product;

import co.istad.dealkh.domain.Product;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {
    Optional<Product> findByName(String productName);

    Optional<Long> findIdByName(String s);

    boolean existsById(@NotNull Long id);

    List<Product> findAllByShopId(Long shopId);
    
    Optional<Product> findBySlug(String slug);


    List<Product> findByCreatedByAndSlug(String username, String slug);
}
