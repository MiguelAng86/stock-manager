package com.inditex.stockmanager.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI OpenAPI() {
        return new OpenAPI().info(new Info().title("Stock manager API")
                        .description("")
                        .version("V1")
                        .contact(new Contact().name("Miguel").url("http://localhost:8080").email("")))
                .addServersItem(new Server().url("http://localhost:8080"));
    }
}
