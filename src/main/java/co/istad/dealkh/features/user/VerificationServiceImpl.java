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
        String verificationUrl = "http://localhost:8080/api/v1/auth/verify?token=" + token;
        String body = "Please verify your email by clicking the following link: " + verificationUrl;

        mailService.sendEmail(user.getEmail(), subject, body);
    }
}
