package com.utp.clinica.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI clinicaOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Clínica API")
                        .description("Documentación de la API del sistema clínico")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("UTP")
                                .email("soporte@utp.com"))
                        .license(new License().name("Apache 2.0")));
    }
}

