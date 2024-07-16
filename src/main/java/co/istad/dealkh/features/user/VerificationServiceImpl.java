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
    public void sendVerificationEmail(User user, String token, String verifyUrl) {
        String recipientAddress = user.getEmail();
        String subject = "Email Verification";
        String confirmationUrl = verifyUrl + token;
        String message = "Please click the link below to verify your email address:\n" + confirmationUrl;

        // Use MailService to send the email
        mailService.sendEmail(recipientAddress, subject, confirmationUrl, "verify");
    }
}
