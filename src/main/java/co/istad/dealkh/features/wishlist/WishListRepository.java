package co.istad.dealkh.features.wishlist;

import co.istad.dealkh.domain.Product;
import co.istad.dealkh.domain.User;
import co.istad.dealkh.domain.WishList;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


public interface WishListRepository extends JpaRepository<WishList, Long> {
    Optional<WishList> findByUuid(String uuid);

    void deleteByUuid(String uuid);

    Page<WishList> findByUser(User user, Pageable pageable);

    List<WishList> findAllByProduct_Shop_Slug(String slug);

    Optional<WishList> findByUserUsernameAndProductSlug(String username, String s);

    @Modifying
    @Transactional
    @Query("DELETE FROM ProductRating pr WHERE pr.product.id = :productId")
    void deleteByProductId(@Param("productId") Long productId);

    List<WishList> findByProduct(Product product);
}
