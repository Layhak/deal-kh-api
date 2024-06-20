package co.istad.dealkh.features.resetpassword;

import co.istad.dealkh.base.BaseResponse;
import co.istad.dealkh.domain.ResetPassword;
import co.istad.dealkh.domain.User;
import co.istad.dealkh.features.mail.MailService;
import co.istad.dealkh.features.resetpassword.dto.ResetPasswordRequest;
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
    public BaseResponse<?> updatePassword(ResetPasswordRequest resetPasswordRequest) {
        // Extract fields from resetPasswordRequest
        String email = resetPasswordRequest.email();
        Integer confirmationCode = resetPasswordRequest.confirmationCode();
        String newPassword = resetPasswordRequest.newPassword();
        String confirmPassword = resetPasswordRequest.confirmPassword();

        // Check if newPassword and confirmPassword match
        if (!newPassword.equals(confirmPassword)) {
            return BaseResponse.badRequest("Passwords do not match");
        }

        // Find the user by email
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();

            // Find the most recent ResetPassword entry for the user
            Optional<ResetPassword> optionalResetPassword = resetPasswordRepository.findTopByUserOrderByExpireDateDesc(user);
            if (optionalResetPassword.isPresent()) {
                ResetPassword resetPassword = optionalResetPassword.get();

                // Check if the confirmation code matches and is not expired
                if (resetPassword.getOpt().equals(confirmationCode) && resetPassword.getExpireDate().after(new Date())) {
                    // Update the user's password
                    user.setPassword(passwordEncoder.encode(newPassword)); // Ensure you hash the password before saving
                    userRepository.save(user);

                    // Return success response
                    return BaseResponse.ok("Password updated successfully").setPayload(new ArrayList<>());
                }
                // Return error if confirmation code is invalid or expired
                return BaseResponse.badRequest("Invalid or expired confirmation code").setPayload(new ArrayList<>());
            }
        }

        // Return error if user is not found
        return BaseResponse.notFound("User not found").setPayload(new ArrayList<>());
    }

    private Integer generateOtp() {
        // Generate a random number with 6 digits and return it as an Integer also positive
        Random random = new Random();
        return 100000 + random.nextInt(900000);
    }
}
