package com.luispiquinrey.KnotCommerce.Configuration.RabbitAMQP;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQPublisher {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendMessage(String message, MessageProperties headers) {
        Message amqpMessage = new Message(message.getBytes(), headers);
        rabbitTemplate.send("Neo4jExchange", "routing-neo4j", amqpMessage);
    }
}
