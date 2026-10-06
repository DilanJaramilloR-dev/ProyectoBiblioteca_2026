package edu.itm.proyecto2026.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DocumentationConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(
                        new OpenAPI()
                                .info(new io.swagger.v3.oas.models.info.Info()
                                        .title("API de ejemplo para el curso de programación en Spring Boot")
                                        .version("1.0")
                                        .description("Esto lo usamos para hacer una buena documentacion de mis APIs.")
                                        .contact(new Contact().name("el profe").email("elprofe@elprofe.com")));

    }
}