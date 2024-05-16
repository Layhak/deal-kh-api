package co.istad.deal_kh.dealkhapi.feature.users;

import co.istad.deal_kh.dealkhapi.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
}
