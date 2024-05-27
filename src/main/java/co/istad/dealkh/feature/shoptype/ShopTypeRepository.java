package co.istad.dealkh.feature.shoptype;

import co.istad.dealkh.entity.ShopType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShopTypeRepository extends JpaRepository<ShopType, Long> {
    Optional<ShopType> findByName(String name);
}