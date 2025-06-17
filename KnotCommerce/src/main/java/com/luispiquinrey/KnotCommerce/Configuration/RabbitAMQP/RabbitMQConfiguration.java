package com.luispiquinrey.KnotCommerce.Configuration.RabbitAMQP;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.Connection;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.retry.support.RetryTemplate;

import com.luispiquinrey.KnotCommerce.Controllers.RestControllerProduct;
import com.rabbitmq.client.ShutdownSignalException;


@Configuration
public class RabbitMQConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(RabbitMQConfiguration.class);

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMandatory(true);

        template.setConfirmCallback((correlation, ack, cause) -> {
            if (ack) {
                logger.info("✅ Message confirmed: " + correlation);
            } else {
                logger.warn("❌ Message confirmation failed: " + cause);
            }
        });

        template.setReturnsCallback(returned -> {
            logger.warn("📭 Message returned: " +
                    "\n📦 Body: " + new String(returned.getMessage().getBody()) +
                    "\n📬 Reply Code: " + returned.getReplyCode() +
                    "\n📨 Reply Text: " + returned.getReplyText() +
                    "\n📌 Exchange: " + returned.getExchange() +
                    "\n🎯 Routing Key: " + returned.getRoutingKey());
        });

        RetryTemplate retryTemplate = new RetryTemplate();
        ExponentialBackOffPolicy backOffPolicy = new ExponentialBackOffPolicy();
        backOffPolicy.setInitialInterval(500);
        backOffPolicy.setMultiplier(10.0);
        backOffPolicy.setMaxInterval(1000);
        retryTemplate.setBackOffPolicy(backOffPolicy);

        template.setRetryTemplate(retryTemplate);
        template.setMessageConverter(messageConverter());
        return template;
    }

    @Bean
    public CachingConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory("localhost");
        connectionFactory.setPublisherConfirmType(CachingConnectionFactory.ConfirmType.CORRELATED);
        connectionFactory.setPublisherReturns(true);
        connectionFactory.addConnectionListener(new ConnectionListener() {
            @Override
            public void onCreate(Connection connection) {
                logger.info("🚀 RabbitMQ connection established: " + connection);
            }

            @Override
            public void onClose(Connection connection) {
                logger.warn("🔌 RabbitMQ connection closed: " + connection);
            }

            @Override
            public void onShutDown(ShutdownSignalException signal) {
                logger.error("💥 RabbitMQ shutdown signal received: " + signal.getMessage());
            }
        });
        return connectionFactory;
    }
}
