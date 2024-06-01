//package com.bordify.dtos;
//
//import java.time.LocalDateTime;
//import java.util.UUID;
//
//public abstract class RabbitMessage {
//
//    private String eventId;
//    private String occurredOn;
//    private String routingKey;
//
//    public RabbitMessage() {
//    }
//
//    public RabbitMessage(String routingKey) {
//        this.eventId     = UUID.randomUUID().toString();
//        this.occurredOn  = LocalDateTime.now().toString();
//        this.routingKey = routingKey;
//    }
//
//    public String getEventId() {
//        return eventId;
//    }
//
//    public String getOccurredOn() {
//        return occurredOn;
//    }
//
//    public String getRoutingKey() {
//        return routingKey;
//    }
//}
