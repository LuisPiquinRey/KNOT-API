package com.luispiquinrey.stripe_service;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableRabbit
public class StripeServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(StripeServiceApplication.class, args);
	}

}
