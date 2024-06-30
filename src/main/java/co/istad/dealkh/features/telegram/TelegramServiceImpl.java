package co.istad.dealkh.features.telegram;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class TelegramServiceImpl implements TelegramService {

    public void sendMessage(String chatId, String message) {
        String botToken = "6927992458:AAGCQuoN0Dzzld5-jtmQyTesrjC8TUqI5mY";
        String url = String.format(
                "https://api.telegram.org/bot%s/sendMessage?chat_id=%s&text=%s",
                botToken, chatId, message
        );
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getForObject(url, String.class);
    }
}
