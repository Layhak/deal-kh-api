package co.istad.dealkh.features.mail;

import co.istad.dealkh.domain.User;

public interface MailService {
    void sendEmail(String to, String subject, String text, String template);
}
