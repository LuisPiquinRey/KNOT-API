package com.luispiquinrey.KnotCommerce;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class KnotCommerceApplication implements CommandLineRunner{

	public static void main(String[] args) {
		new SpringApplicationBuilder().sources(KnotCommerceApplication.class)
				.properties("spring.config.location=classpath:/application.yml")
				.web(WebApplicationType.SERVLET)
				.profiles("dev")
				.run(args);
	}

	@Override
	public void run(String... args) throws Exception {}
}
