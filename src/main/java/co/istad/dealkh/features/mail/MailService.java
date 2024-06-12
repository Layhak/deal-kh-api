package co.istad.dealkh.features.mail;

public interface MailService {
    void sendSimpleEmail(String to, String subject, String text);
}
