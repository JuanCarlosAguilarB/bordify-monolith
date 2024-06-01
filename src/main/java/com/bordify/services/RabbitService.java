//package com.bordify.services;
//
//import com.bordify.dtos.RabbitMessage;
//import org.springframework.amqp.rabbit.core.RabbitTemplate;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//@Service
//public class RabbitService {
//
//    @Autowired
//    private RabbitTemplate rabbitTemplate;
//    public void publish(RabbitMessage domainEvent) {
//
//        String routingKey = domainEvent.getRoutingKey();
//
//        rabbitTemplate.convertAndSend(routingKey, domainEvent);
//    }
//
//}
