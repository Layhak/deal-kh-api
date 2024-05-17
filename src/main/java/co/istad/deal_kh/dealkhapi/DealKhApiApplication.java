package co.istad.deal_kh.dealkhapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class DealKhApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(DealKhApiApplication.class, args);
    }
}
