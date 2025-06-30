package com.example.credit_card_api.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/credit-cards")
            .allowedOrigins("http://localhost:4200, http://localhost:9876")
            .allowedMethods("GET", "POST")
            .allowedHeaders("*")
            .allowCredentials(true);
    }
}