package com.luispiquinrey.stripe_service.RabbitAMQP;

import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConsumerConfig {
    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(
            ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMissingQueuesFatal(false);
        factory.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        factory.setFailedDeclarationRetryInterval(5000L);
        return factory;
    }
    @Bean
    public Queue queue(){
        return QueueBuilder.durable("StripeQueue").build();
    }
    @Bean
    public DirectExchange exchange(){
        return new DirectExchange("ExchangeKNOT");
    }
    @Bean
    public Binding binding(Queue queue, Exchange exchange){
        return BindingBuilder.bind(queue)
            .to(exchange)
            .with("routing-stripe")
            .noargs();
    }
}
