package co.istad.dealkh.features.user;

import co.istad.dealkh.domain.User;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    @NotNull
    Page<User> findAll(@NotNull Pageable pageable);

    //get all disable users
    List<User> findAllByIsDisabledFalse();

    List<User> findAllByIsDisabledTrue();

    Optional<User> findByUsername(String username);

    User getUserById(Long id);

    Optional<Long> findIdByUsername(String username);

    Optional<User> findByEmail(String email);

//    boolean existsByPhone(String s);
//
////    boolean existsByPhone(String s);
}
