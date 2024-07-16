package co.istad.dealkh.features.product;

import co.istad.dealkh.domain.Product;
import co.istad.dealkh.domain.Shop;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {
    Optional<Product> findByName(String productName);

    Optional<Long> findIdByName(String s);

    boolean existsById(@NotNull Long id);

    List<Product> findAllByShopId(Long shopId);

    Optional<Product> findBySlug(String slug);

    List<Product> findByCreatedByAndSlug(String username, String slug);

    List<Product> findAllBySlugIn(List<String> strings);

    Page<Product> findAllProductByShopSlug(String slug, Pageable pageable);

    Optional<Product> findByShopId(Long id);

    Page<Product> findAllByShopUsersUsername(String username, Pageable pageable);

    List<Product> findByShop(Shop shop);

    Optional<Product> findByShopSlug(String shopSlug);
    List<Product> findAllByShopSlug(String slug);

    Page<Product> findAllByShopContains(String username, Pageable pageable);

    @Query("SELECT p FROM Product p JOIN p.shop s JOIN s.users u WHERE u.username = :username AND " +
            "(:name IS NULL OR p.name LIKE %:name%) AND " +
            "(:discountValue IS NULL OR p.discountPrice = :discountValue) AND " +
            "(:discountType IS NULL OR p.discount.discountType = :discountType) AND " +
            "(:category IS NULL OR p.category.name = :category) AND " +
            "(:shop IS NULL OR s.name = :shop)")
    Page<Product> findAllByUserControl(@Param("username") String username,
                                       @Param("name") String name,
                                       @Param("discountValue") Double discountValue,
                                       @Param("discountType") String discountType,
                                       @Param("category") String category,
                                       @Param("shop") String shop,
                                       Pageable pageable);
}
