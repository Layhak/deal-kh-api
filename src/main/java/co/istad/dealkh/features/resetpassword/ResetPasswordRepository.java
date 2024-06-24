package co.istad.dealkh.features.resetpassword;

import co.istad.dealkh.domain.ResetPassword;
import co.istad.dealkh.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ResetPasswordRepository extends JpaRepository<ResetPassword, Long> {

    Optional<ResetPassword> findByOtp(Integer otp);

    Optional<ResetPassword> findByOtpAndIsConfirmed(Integer otp, Boolean isConfirmed);


}
