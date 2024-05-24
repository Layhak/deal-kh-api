package co.istad.dealkh.feature.Authorities;

import co.istad.dealkh.entity.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {
}
