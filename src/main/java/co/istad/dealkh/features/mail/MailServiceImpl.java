package co.istad.dealkh.features.mail;

import co.istad.dealkh.config.MailProperties;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {

    private final MailProperties mailProperties;
    private final JavaMailSender javaMailSender;
    private final SpringTemplateEngine templateEngine;

    @Override
    public void sendEmail(String to, String subject, String content, String template) {
        try {
            String htmlContent = loadHtmlTemplate(content, template);
            sendHtmlEmail(to, subject, htmlContent);
        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send email", e);
        }
    }

    private String loadHtmlTemplate(String content, String template) {
        Context context = new Context();
        context.setVariable("content", content);
        return templateEngine.process(template, context);
    }

    private void sendHtmlEmail(String to, String subject, String htmlContent) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");

        helper.setText(htmlContent, true); // true indicates HTML
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setFrom(mailProperties.getFromEmail());

        javaMailSender.send(mimeMessage);
    }
}
