package co.istad.dealkh.feature.Authorities;

import co.istad.dealkh.domain.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {
}
