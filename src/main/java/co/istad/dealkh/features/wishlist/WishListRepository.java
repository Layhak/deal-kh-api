package co.istad.dealkh.features.wishlist;

import co.istad.dealkh.domain.WishList;
import org.springframework.data.jpa.repository.JpaRepository;


public interface WishListRepository extends JpaRepository<WishList, Long> {
}
