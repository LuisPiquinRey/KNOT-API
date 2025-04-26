package com.luispiquinrey.apiknot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class ApiKnotApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiKnotApplication.class, args);
	}

}
