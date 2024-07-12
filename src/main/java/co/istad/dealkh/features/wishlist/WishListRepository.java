package co.istad.dealkh.features.wishlist;

import co.istad.dealkh.domain.Shop;
import co.istad.dealkh.domain.User;
import co.istad.dealkh.domain.WishList;
import com.fasterxml.jackson.databind.introspect.AnnotationCollector;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface WishListRepository extends JpaRepository<WishList, Long> {
    Optional<WishList> findByUuid(String uuid);

    void deleteByUuid(String uuid);

    Page<WishList> findByUser(User user, Pageable pageable);

    List<WishList> findAllByProduct_Shop_Slug(String slug);

    Optional<WishList> findByUserUsernameAndProductSlug(String username, String s);
}
