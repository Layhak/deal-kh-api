package co.istad.dealkh.features.wishlist;

import co.istad.dealkh.domain.User;
import co.istad.dealkh.domain.WishList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface WishListRepository extends JpaRepository<WishList, Long> {
    Optional<WishList> findByUuid(String uuid);

    void deleteByUuid(String uuid);

    WishList findByUser(User user);
}
