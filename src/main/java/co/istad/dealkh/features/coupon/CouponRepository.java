package co.istad.dealkh.features.coupon;

import co.istad.dealkh.domain.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CouponRepository extends JpaRepository<Coupon, Long> {

    Optional<Coupon> findByCode(String code);

    boolean existsByCode(String code);

    Optional<Long> findIdByCode(String code);

    Optional<Coupon> findByCreatedByAndCode(String username, String code);

    List<Coupon> findAllByUsersUsername(String username);
}
