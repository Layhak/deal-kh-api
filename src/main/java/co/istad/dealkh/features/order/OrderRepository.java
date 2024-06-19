package co.istad.dealkh.features.order;

import co.istad.dealkh.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllByUserId(Long userId);


    void deleteByUuid(String orderUuid);

    List<Order> findAllByUserUsername(String username);

    Optional<Order> findByUuid(String orderUuid);
}
