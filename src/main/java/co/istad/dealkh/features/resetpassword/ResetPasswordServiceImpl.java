package co.istad.dealkh.features.resetpassword;

import co.istad.dealkh.base.BaseResponse;
import co.istad.dealkh.domain.ResetPassword;
import co.istad.dealkh.domain.User;
import co.istad.dealkh.features.mail.MailService;
import co.istad.dealkh.features.resetpassword.dto.ConfirmOtpCode;
import co.istad.dealkh.features.resetpassword.dto.ResetPasswordRequest;
import co.istad.dealkh.features.resetpassword.dto.SentOtpRequest;
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
    public BaseResponse<?> sendOtp(SentOtpRequest sentOtpRequest) {
        Optional<User> optionalUser = userRepository.findByEmail(sentOtpRequest.email());
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            Integer otp = generateOtp();
            Date expireDate = new Date(System.currentTimeMillis() + 5 * 60 * 1000); // 5 minute

            ResetPassword resetPassword = new ResetPassword();
            resetPassword.setOtp(otp);
            resetPassword.setExpireDate(expireDate);
            resetPassword.setUser(user);

            resetPasswordRepository.save(resetPassword);

            emailService.sendEmail(user.getEmail(), "Verification", otp.toString(), "otp");
            return BaseResponse.ok("OTP sent to your email").setPayload(new ArrayList<>());
        }
        return BaseResponse.notFound("User not found").setPayload(new ArrayList<>());
    }

    @Override
    public BaseResponse<?> confirmOtp(ConfirmOtpCode confirmOtpCode) {
        Optional<ResetPassword> resetPasswordOpt = resetPasswordRepository.findByOtp(confirmOtpCode.otp());
        if (resetPasswordOpt.isPresent()) {
            ResetPassword resetPassword = resetPasswordOpt.get();
            if (resetPassword.getExpireDate().after(new Date())) {
                resetPassword.setIsConfirmed(true);
                resetPasswordRepository.save(resetPassword);
                return BaseResponse.ok("OTP confirmed. Now you can reset your password.").setPayload(new ArrayList<>());
            } else {
                return BaseResponse.notFound("OTP has expired.").setPayload(new ArrayList<>());
            }
        } else {
            return BaseResponse.notFound("Invalid OTP.").setPayload(new ArrayList<>());
        }
    }

    @Override
    public BaseResponse<?> resetPassword(ResetPasswordRequest resetPasswordRequest) {
        if (!resetPasswordRequest.newPassword().equals(resetPasswordRequest.confirmPassword())) {
            return BaseResponse.badRequest("Passwords do not match.").setPayload(new ArrayList<>());
        }

        Optional<ResetPassword> resetPasswordOpt = resetPasswordRepository.findByOtpAndIsConfirmed(resetPasswordRequest.otp(), true);
        if (resetPasswordOpt.isPresent()) {
            ResetPassword resetPassword = resetPasswordOpt.get();
            if (resetPassword.getExpireDate().after(new Date())) {
                User user = resetPassword.getUser();
                System.out.println("EMAIL:"+user.getEmail());
                System.out.println("PASSWORD:"+user.getPassword());
                user.setPassword(passwordEncoder.encode(resetPasswordRequest.newPassword()));
                userRepository.save(user);

                resetPasswordRepository.delete(resetPassword); // Delete the OTP record after successful reset

                return BaseResponse.ok("Password has been reset successfully.").setPayload(new ArrayList<>());
            } else {
                return BaseResponse.notFound("OTP has expired.").setPayload(new ArrayList<>());
            }
        } else {
            return BaseResponse.notFound("Invalid OTP.").setPayload(new ArrayList<>());
        }
    }

    private int generateOtp() {
        Random random = new Random();
        return 100000 + random.nextInt(900000);
    }

}
