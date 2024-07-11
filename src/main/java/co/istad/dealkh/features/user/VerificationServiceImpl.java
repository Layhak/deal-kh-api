package co.istad.dealkh.features.user;

import co.istad.dealkh.domain.User;
import co.istad.dealkh.features.mail.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VerificationServiceImpl implements VerificationService {

    private final MailService mailService;

    @Override
    public void sendVerificationEmail(User user, String token) {
        String subject = "Verify your email address";
        String verificationUrl = "https://dealkh-api.istad.co/api/v1/auth/verify?token=" + token;

        mailService.sendEmail(user.getEmail(), subject, verificationUrl, "verify");
    }
}
