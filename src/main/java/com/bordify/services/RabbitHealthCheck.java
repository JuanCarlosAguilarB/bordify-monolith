package com.bordify.services;



import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RabbitHealthCheck {

    @Autowired
    private ConnectionFactory connectionFactory;

    public void checkRabbitMQConnection() {
        try {
            // Intenta crear una conexión con RabbitMQ
            connectionFactory.createConnection();
            System.out.println("La conexión con RabbitMQ fue exitosa.");
        } catch (Exception e) {
            System.err.println("No se pudo establecer la conexión con RabbitMQ: " + e.getMessage());
        }
    }
}
