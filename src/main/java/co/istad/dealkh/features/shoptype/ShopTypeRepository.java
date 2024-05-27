package co.istad.dealkh.features.shoptype;

import co.istad.dealkh.domain.ShopType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface ShopTypeRepository extends JpaRepository<ShopType, Long>, JpaSpecificationExecutor<ShopType> {

    Optional<ShopType> findByName(String name);

    boolean existsByName(String name);
}
