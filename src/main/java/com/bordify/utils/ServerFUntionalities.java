package com.bordify.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Field;

@EnableScheduling
@RestController
public class ServerFUntionalities{


    private final String url = "https://para-bordify.onrender.com/docs";
//https://bordify-monolith.onrender.com/api-docs/swagger-ui/index.html#/
    @Scheduled(fixedRate = 30000) // Llamada cada 30 segundos
    public void callWebsite() {
        RestTemplate restTemplate = new RestTemplate();
//        int statusCode = restTemplate.getForObject(url, String.class).getStatusCodeValue();
        ResponseEntity<String> response
                = restTemplate.getForEntity(url, String.class);
        if (response.getStatusCode() != HttpStatus.OK) {
            System.out.println("Error: ");
        } else {
            System.out.println("Success: ");
        }
    }


}