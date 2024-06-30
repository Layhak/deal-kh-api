package co.istad.dealkh.features.telegram;

public interface TelegramService {
    void sendMessage(String chatId, String message);
}
