package com.luispiquinrey.KnotCommerce;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.luispiquinrey.KnotCommerce.Entities.Product.NoPerishableProduct;
import com.luispiquinrey.KnotCommerce.Entities.Product.Product;
import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;

@SpringBootApplication
@EnableEncryptableProperties
@EnableScheduling
@EnableAsync
public class KnotCommerceApplication implements CommandLineRunner{

	Logger log=LoggerFactory.getLogger(KnotCommerceApplication.class);

	public static void main(String[] args) {
		new SpringApplicationBuilder().sources(KnotCommerceApplication.class)
				.web(WebApplicationType.SERVLET)
				.profiles("dev")
				.run(args);
	}

	@Override
	public void run(String... args) throws Exception {
		log.info("Creating test product to generate QR code...");

    	Product product = new NoPerishableProduct();
    	product.setId_Product(999L);
    	product.setName("Test QR Product");
    	product.setPrice(19.99);
    	product.setDescription("Product for QR code test");
    	product.setStock(10);
    	product.setAvailable(true);
    	product.generateQR();

    	log.info("Test product QR code generated.");
	}
}
