package co.istad.dealkh.features.user;

import co.istad.dealkh.domain.User;
import co.istad.dealkh.features.mail.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VerificationServiceImpl implements VerificationService {

    private final UserRepository userRepository;
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

    @Override
    @Scheduled(cron = "0 0 0 * * ?") // Runs daily at midnight
    public void removeUnverifiedUsers() {
        LocalDateTime now = LocalDateTime.now();
        List<User> unverifiedUsers = userRepository.findUnverifiedUsersWithExpiredToken(now);
        userRepository.deleteAll(unverifiedUsers);
    }
}
