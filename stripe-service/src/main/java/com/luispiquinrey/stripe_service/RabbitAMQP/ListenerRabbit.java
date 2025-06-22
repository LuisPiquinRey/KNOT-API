package com.luispiquinrey.stripe_service.RabbitAMQP;

import java.io.IOException;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.rabbitmq.client.Channel;

@Component
public class ListenerRabbit {
    @RabbitListener(queues = "StripeQueue", containerFactory = "rabbitListenerContainerFactory")
    public void receiveMessage(String payload, Channel channel, Message message) throws IOException {
        try {
            System.out.println("Message: " + payload);
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (Exception e) {
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
        }
    }
}
