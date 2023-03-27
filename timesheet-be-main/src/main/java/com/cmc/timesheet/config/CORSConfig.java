package com.cmc.timesheet.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CORSConfig {
    @Value("${timesheet.http.cors.origins:http://localhost:3000,http://192.168.78.10:3000}")
    private String[] origins;

    @Value("${timesheet.http.cors.methods:GET,OPTIONS,POST,PUT,PATCH,DELETE}")
    private String[] methods;

    @Value("${timesheet.http.cors.headers:*}")
    private String headers;

    @Bean
    public WebMvcConfigurer cors() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("*")
                        .allowedMethods(methods)
                        .allowedHeaders(headers)
                ;
            }
        };
    }
}
