package com.luispiquinrey.apiknot;

import io.sentry.Sentry;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableCaching
@EnableAspectJAutoProxy
public class ApiKnotApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ApiKnotApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		try {
			throw new Exception("This is a test.");
		} catch (Exception e) {
			Sentry.captureException(e);
		}
	}
}
