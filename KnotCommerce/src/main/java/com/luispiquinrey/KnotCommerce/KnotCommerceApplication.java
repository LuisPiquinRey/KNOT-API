package com.luispiquinrey.KnotCommerce;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication
@EnableEncryptableProperties
@EnableScheduling
@EnableAsync
@EnableCaching
@EnableRabbit
@EnableFeignClients
@OpenAPIDefinition(
	info=@Info(
		title="Product microservice REST API Documentation",
		description="REST API for managing the Product class, featuring a simple CRUD that implements modern" +
		"practices and particular features such as QR code generation and handling large amounts of information through Spring Batch."
		+ "Thanks to integration with the email management microservice, when a user purchases a product, they receive a confirmation email.",
		version="v1",
		contact=@Contact(
			name="Luis Piquin Rey",
			email="piquin.rey@gmail.com"
		)
	)
)
public class KnotCommerceApplication implements CommandLineRunner{

	Logger log=LoggerFactory.getLogger(KnotCommerceApplication.class);

	@Autowired
	private ApplicationContext appContext;

	public static void main(String[] args) {
		new SpringApplicationBuilder().sources(KnotCommerceApplication.class)
				.web(WebApplicationType.SERVLET)
				.profiles("dev")
				.run(args);
	}

	@Override
	public void run(String... args) throws Exception {
		
	}
}
