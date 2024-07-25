package co.istad.dealkh.features.user;

import co.istad.dealkh.domain.User;
import jakarta.transaction.Transactional;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    @NotNull
    Page<User> findAll(@NotNull Pageable pageable);

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    Page<User> findAllUserByRoles_Name(String role, Pageable pageable);

    Optional<User> findUserByUsername(String username);

    boolean existsByPhoneNumber(String phoneNumber);

    Optional<User> findByVerificationToken(String verificationToken);

    List<User> findAllByShopsSlug(String slug);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM dk_user_shops WHERE user_id = ?1", nativeQuery = true)
    void deleteUserShopsByUserId(Long userId);

    @Query("SELECT u FROM User u WHERE u.isVerified = false AND u.tokenExpiryDate < :now")

    List<User> findUnverifiedUsersWithExpiredToken(LocalDateTime now);
}
