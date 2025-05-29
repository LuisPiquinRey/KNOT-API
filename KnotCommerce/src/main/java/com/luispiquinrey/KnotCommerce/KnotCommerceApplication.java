package com.luispiquinrey.KnotCommerce;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;

@SpringBootApplication
@EnableEncryptableProperties
@EnableScheduling
@EnableAsync
public class KnotCommerceApplication implements CommandLineRunner{

	public static void main(String[] args) {
		new SpringApplicationBuilder().sources(KnotCommerceApplication.class)
				.web(WebApplicationType.SERVLET)
				.profiles("dev")
				.run(args);
	}

	@Override
	public void run(String... args) throws Exception {}
}
