package co.istad.dealkh.features.Authority;

import co.istad.dealkh.domain.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {
}
