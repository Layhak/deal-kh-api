package co.istad.deal_kh.dealkhapi.feature.Authorities;

import co.istad.deal_kh.dealkhapi.domain.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {
}
