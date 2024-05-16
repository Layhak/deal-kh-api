package co.istad.deal_kh.dealkhapi.feature.roles;

import co.istad.deal_kh.dealkhapi.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

}
