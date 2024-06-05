package co.istad.dealkh;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
@OpenAPIDefinition(
        info = @Info(
                title = "Mobile Banking API (Data Analytics Class )",
                version = "1.0",
                description = "Mobile Banking API for Data Analytics Class 2024",
                contact = @Contact(
                        name = "Admin",
                        email = "admin@gmail.com"

                ),
                termsOfService = "http://swagger.io/terms/",
                license = @io.swagger.v3.oas.annotations.info.License(
                        name = "Apache 2.0",
                        url = "http://www.apache.org/licenses/LICENSE-2.0.html"
                )
        ),
        security = @SecurityRequirement(name = "bearerAuth")
)

@SecurityScheme(
        name = "bearerAuth",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)

public class DealKhApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(DealKhApiApplication.class, args);
    }

}
