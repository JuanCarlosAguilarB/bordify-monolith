//package com.bordify.config;
//
//import org.springframework.amqp.core.*;
//import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
//import org.springframework.amqp.rabbit.core.RabbitTemplate;
//import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
//import org.springframework.amqp.support.converter.MessageConverter;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.amqp.rabbit.connection.ConnectionFactory;
//
//
//@Configuration
//public class RabbitMQConfig {
//
//    @Value("${spring.rabbitmq.host}")
//    String host;
//
//    @Value("${spring.rabbitmq.username}")
//    String username;
//
//    @Value("${spring.rabbitmq.password}")
//    String password;
//    @Value("${spring.rabbitmq.port}")
//    int port;
//
////    @Value("${rabbitmq.queue}")
////    String queueName;
////
//    @Value("${spring.rabbitmq.exchange}")
//    String exchange;
//
////    @Value("${rabbitmq.routingkey}")
////    private String routingkey;
////
////    @Bean
////    Queue queue() {
////        return new Queue(queueName, false);
////    }
////
////
//    @Bean
//    DirectExchange exchange() {
//        return new DirectExchange(exchange);
//    }
////
////    @Bean
////    Binding binding(Queue queue, DirectExchange exchange) {
////        return BindingBuilder.bind(queue).to(exchange).with(routingkey);
////    }
//
//    @Bean
//    public MessageConverter jsonMessageConverter() {
//        return new Jackson2JsonMessageConverter();
//    }
//
//    @Bean
//    public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory) {
//        final RabbitTemplate template = new RabbitTemplate(connectionFactory);
//        template.setMessageConverter(jsonMessageConverter());
//        return template;
//    }
//
//
//    // RabbitMQ connection settings
//    @Bean
//    public ConnectionFactory connectionFactory() {
//
//
//        CachingConnectionFactory connectionFactory = new CachingConnectionFactory(host);
//        connectionFactory.setUsername(username);
//        connectionFactory.setPassword(password);
//        return connectionFactory;
//    }
//
//
//}