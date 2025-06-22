package com.luispiquinrey.KnotCommerce.Configuration.RabbitAMQP;

import java.util.UUID;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.luispiquinrey.KnotCommerce.DTOs.ProductPaymentDTO;

@Component
public class RabbitMQPublisher {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendMessageStripe(ProductPaymentDTO productPaymentDTO){
        CorrelationData correlationData=new CorrelationData(UUID.randomUUID().toString());
        rabbitTemplate.convertAndSend("ExchangeKNOT","routing-stripe", productPaymentDTO,correlationData);
    }
}
