package co.istad.dealkh.features.Authorities;

import co.istad.dealkh.entity.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {
}
