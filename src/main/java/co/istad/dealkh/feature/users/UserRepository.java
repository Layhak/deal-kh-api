package co.istad.dealkh.feature.users;

import co.istad.dealkh.entity.User;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    @NotNull
    Page<User> findAll(@NotNull Pageable pageable);

   //get user by status(disable or enable)

    @Query("SELECT u FROM User u WHERE u.isDisabled = ?1")
    List<User> findAllByStatus(boolean status);

}
