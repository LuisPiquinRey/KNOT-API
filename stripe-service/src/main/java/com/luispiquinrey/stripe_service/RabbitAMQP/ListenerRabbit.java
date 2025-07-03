package com.luispiquinrey.stripe_service.RabbitAMQP;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.stripe.Stripe;
import com.stripe.model.Price;
import com.stripe.model.Product;
import com.stripe.param.PriceCreateParams;
import com.stripe.param.ProductCreateParams;
import com.stripe.param.ProductUpdateParams;

@Component
public class ListenerRabbit {

    private static final Logger logger = LoggerFactory.getLogger(ListenerRabbit.class);

    @RabbitListener(queues = "StripeQueue", containerFactory = "rabbitListenerContainerFactory")
    public void receiveMessage(String payload, Channel channel, Message message) throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(payload);
        String apiKey = System.getenv("STRIPE_API_KEY");
        if (apiKey == null || apiKey.isEmpty()) {
            throw new RuntimeException("STRIPE_API_KEY is not set");
        }
        Stripe.apiKey = apiKey;
        try {
            switch (jsonNode.get("tactic").asText()) {
                case "CREATE_PRODUCT" -> {
                    ProductCreateParams productParams = ProductCreateParams.builder()
                            .setName(jsonNode.get("name").asText())
                            .setDescription(jsonNode.get("description").asText())
                            .setActive(true)
                            .setId(jsonNode.get("id_Product").asText())
                            .build();
                    Product product = Product.create(productParams);
                    logger.info("Success! Here is your starter product id: " + product.getId());
                    PriceCreateParams params = PriceCreateParams.builder()
                            .setProduct(product.getId())
                            .setCurrency("usd")
                            .setUnitAmount(jsonNode.get("price").asLong())
                            .build();
                    Price price = Price.create(params);
                    logger.info("Success! Here is the id of the price: " + price.getId());
                    channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
                    break;
                }
                case "DELETE_PRODUCT" -> {
                    Product product = Product.retrieve(jsonNode.get("id_Product").asText());
                    Product deletedProduct = product.delete();
                    logger.info("Product deleted: " + deletedProduct.getId());
                    channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
                    break;
                }
                case "UPDATE_PRODUCT" -> {
                    String productId = jsonNode.get("id_Product").asText();
                    ProductUpdateParams updateParams = ProductUpdateParams.builder()
                            .setName(jsonNode.has("name") ? jsonNode.get("name").asText() : null)
                            .setDescription(jsonNode.has("description") ? jsonNode.get("description").asText() : null)
                            .setActive(jsonNode.has("active") ? jsonNode.get("active").asBoolean() : null)
                            .build();

                    Product product= Product.retrieve(jsonNode.get("id_Product").asText());
                    Product updateProduct=product.update(updateParams);
                    logger.info("Product updated successfully: " + updateProduct.getId());

                    channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
                    break;
                }
            }
        } catch (Exception e) {
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, false);
        }
    }
}
