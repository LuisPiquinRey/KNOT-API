package com.luispiquinrey.graph_migrator_service.RabbitAMQP;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class ListenerRabbit{

    @RabbitListener(queues="Neo4jQueue")
    public void receive(@Payload String message){
        System.out.println("Hola: " + message);
    }
    @Bean
    public Queue queue() {
        return new Queue("Neo4jQueue", false);
    }
}
