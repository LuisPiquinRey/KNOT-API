package com.luispiquinrey.KnotCommerce.Configuration.RabbitAMQP;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RabbitMQConfiguration {
    @Bean
    public Queue queue(){
        return new Queue("Neo4jQueue",false);
    }
    @Bean
    public Exchange exchange(){
        return new DirectExchange("Neo4jExchange");
    }
    @Bean
    public Binding binding(Queue queue, Exchange exchange){
        return BindingBuilder.bind(queue)
            .to(exchange)
            .with("routing-neo4j")
            .noargs();
    }
    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(messageConverter());
        return template;
    }
}
