package co.istad.dealkh.features.mail;

public interface MailService {
    void sendEmail(String to, String subject, String text);
}
