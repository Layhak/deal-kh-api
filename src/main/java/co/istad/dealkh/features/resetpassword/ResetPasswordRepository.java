package co.istad.dealkh.features.resetpassword;

import co.istad.dealkh.domain.ResetPassword;
import co.istad.dealkh.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ResetPasswordRepository extends JpaRepository<ResetPassword, Long> {

    Optional<ResetPassword> findTopByUserOrderByExpireDateDesc(User user);

    Optional<ResetPassword> findTopByOtpOrderByExpireDateDesc(Integer otp);

    Optional<ResetPassword> findTopByUserAndOtpOrderByExpireDateDesc(User user, Integer otp);

    Optional<ResetPassword> findByOtp(Integer otp);


}
