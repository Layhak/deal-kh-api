package co.istad.dealkh.features.user;

import co.istad.dealkh.domain.Role;
import co.istad.dealkh.domain.User;
import co.istad.dealkh.domain.json.Image;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

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

    Optional<User> findByUsernameAndProfile(String username, String profile);

    User getUserById(Long id);

    Optional<Long> findIdByUsername(String username);

    Optional<User> findByEmail(String email);

    Page<User> findAllUserByRoles_Name(String role, Pageable pageable);

    Optional<User> findUserByUsername(String username);

    boolean existsByPhoneNumber(String phoneNumber);

    Optional<User> findByVerificationToken(String verificationToken);

    Optional<User> findByUsernameAndCovers(String username, List<Image> cover);

//    boolean existsByPhone(String s);
//
////    boolean existsByPhone(String s);
}
