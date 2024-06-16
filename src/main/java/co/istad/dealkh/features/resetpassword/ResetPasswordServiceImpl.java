package co.istad.dealkh.features.resetpassword;

import co.istad.dealkh.base.BaseResponse;
import co.istad.dealkh.domain.ResetPassword;
import co.istad.dealkh.domain.User;
import co.istad.dealkh.features.mail.MailService;
import co.istad.dealkh.features.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class ResetPasswordServiceImpl implements ResetPasswordService {
    private final ResetPasswordRepository resetPasswordRepository;
    private final UserRepository userRepository;
    private final MailService emailService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public BaseResponse<?> sendOtp(String email) {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            Integer otp = generateOtp();
            Date expireDate = new Date(System.currentTimeMillis() + 30 * 60 * 1000); // 30 minutes

            ResetPassword resetPassword = new ResetPassword();
            resetPassword.setOpt(otp);
            resetPassword.setExpireDate(expireDate);
            resetPassword.setUser(user);

            resetPasswordRepository.save(resetPassword);

            emailService.sendSimpleEmail(user.getEmail(), "Your OTP Code", "Your OTP code is " + otp);
            return BaseResponse.ok("OTP sent to your email").setPayload(new ArrayList<>());
        }
        return BaseResponse.notFound("User not found").setPayload(new ArrayList<>());
    }

    @Override
    public BaseResponse<?> confirmOtp(String email, Integer opt) {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            Optional<ResetPassword> optionalResetPassword = resetPasswordRepository.findTopByUserOrderByExpireDateDesc(user);
            if (optionalResetPassword.isPresent()) {
                ResetPassword resetPassword = optionalResetPassword.get();
                if (resetPassword.getOpt().equals(opt) && resetPassword.getExpireDate().after(new Date())) {
                    return BaseResponse.ok("OTP confirmed. You can now reset your password.").setPayload(new ArrayList<>());
                }
                return BaseResponse.badRequest("Invalid or expired OTP").setPayload(new ArrayList<>());
            }
        }
        return BaseResponse.notFound("User not found").setPayload(new ArrayList<>());
    }

    @Override
    public BaseResponse<?> updatePassword(String email, Integer confirmationCode, String newPassword, String confirmPassword) {
        if (!newPassword.equals(confirmPassword)) {
            return BaseResponse.badRequest("Passwords do not match");
        }
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            Optional<ResetPassword> optionalResetPassword = resetPasswordRepository.findTopByUserOrderByExpireDateDesc(user);
            if (optionalResetPassword.isPresent()) {
                ResetPassword resetPassword = optionalResetPassword.get();
                if (resetPassword.getOpt().equals(confirmationCode) && resetPassword.getExpireDate().after(new Date())) {
                    user.setPassword(passwordEncoder.encode(newPassword)); // Ensure you hash the password before saving
                    userRepository.save(user);
                    return (BaseResponse.ok("Password updated successfully")).setPayload(new ArrayList<>());
                }
                return BaseResponse.badRequest("Invalid or expired confirmation code").setPayload(new ArrayList<>());
            }
        }
        return BaseResponse.notFound("User not found").setPayload(new ArrayList<>());
    }

    private Integer generateOtp() {
        // Generate a random number with 6 digits and return it as an Integer also positive
        Random random = new Random();
        return 100000 + random.nextInt(900000);
    }
}
