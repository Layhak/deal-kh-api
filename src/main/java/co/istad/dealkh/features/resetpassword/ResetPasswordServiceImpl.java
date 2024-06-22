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
        Optional<User> userOpt = userRepository.findByEmail(sentOtpRequest.email());
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            int otp = generateOtp();
            Date expireDate = new Date(System.currentTimeMillis() + 15 * 60 * 1000); // OTP valid for 15 minutes

            ResetPassword resetPassword = new ResetPassword();
            resetPassword.setUser(user);
            resetPassword.setOtp(otp);
            resetPassword.setExpireDate(expireDate);
            resetPasswordRepository.save(resetPassword);

            emailService.sendSimpleEmail(user.getEmail(), "Your OTP Code", "Your OTP code is " + otp);
            return BaseResponse.ok("OTP has been sent to your email.").setPayload(new ArrayList<>());
        } else {
            return BaseResponse.notFound("User with this email does not exist!").setPayload(new ArrayList<>());
        }
    }



    @Override
    public BaseResponse<?> confirmOtp(ConfirmOtpCode confirmOtpCode) {
        Optional<ResetPassword> resetPasswordOpt = resetPasswordRepository.findByOtp(confirmOtpCode.otp());
        if (resetPasswordOpt.isPresent()) {
            ResetPassword resetPassword = resetPasswordOpt.get();
            if (resetPassword.getExpireDate().after(new Date())) {
                return BaseResponse.ok("OTP is valid.").setPayload(new ArrayList<>());
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
            return BaseResponse.notFound("Passwords do not match.").setPayload(new ArrayList<>());
        }

        Optional<ResetPassword> resetPasswordOpt = resetPasswordRepository.findByOtp(resetPasswordRequest.otp());
        if (resetPasswordOpt.isPresent()) {
            ResetPassword resetPassword = resetPasswordOpt.get();
            if (resetPassword.getExpireDate().after(new Date())) {
                User user = resetPassword.getUser();
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
