package co.istad.dealkh.features.grantedwishlist;

import co.istad.dealkh.domain.GrantedWishList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GrantedWishListRepository extends JpaRepository<GrantedWishList, Long> {
}
