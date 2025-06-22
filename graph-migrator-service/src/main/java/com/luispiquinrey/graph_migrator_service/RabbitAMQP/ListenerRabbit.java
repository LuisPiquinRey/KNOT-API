package com.luispiquinrey.graph_migrator_service.RabbitAMQP;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class ListenerRabbit{

    @RabbitListener(queues="Neo4jQueue")
    public void receive(@Payload String message){
        System.out.println("Hola: " + message);
    }
}
