package com.bordify.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuration class for defining CORS (Cross-Origin Resource Sharing) policies.
 * Enables cross-origin requests from specified origins, methods, and headers.
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    /**
     * Configures CORS mappings to allow cross-origin requests from specified origins,
     * methods, and headers for all endpoints.
     *
     * @param registry The CORS registry to configure CORS mappings.
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:5173", "https://boardify-psi.vercel.app/") // Allow requests from this origin
                .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS") // Allow these HTTP methods
                .allowedHeaders("*"); // Allow all headers in requests
    }
}