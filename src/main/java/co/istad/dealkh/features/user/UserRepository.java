package co.istad.dealkh.features.user;

import co.istad.dealkh.entity.User;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    @NotNull
    Page<User> findAll(@NotNull Pageable pageable);

    //get all disable users
    List<User> findAllByIsDisabledFalse();

    List<User> findAllByIsDisabledTrue();


}
