package com.luispiquinrey.gateway.Configuration;

import java.time.Duration;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

@Component
public class RouteLocatorConfig {
    @Bean
    public RouteLocator routeLocatorAdministration(RouteLocatorBuilder builder) {
        return builder.routes()
            .route("product_route", route -> route
                .path("/product/**")
                .filters(filter -> filter
                    .retry(retryConfig-> retryConfig
                        .setRetries(3)
                        .setMethods(HttpMethod.GET)
                        .setBackoff(Duration.ofMillis(100), Duration.ofMillis(1000), 2, true))
                    .circuitBreaker(configBreaker -> 
                        configBreaker.setName("CircuitBreakerProduct"))
                )
                .uri("lb://KNOTCOMMERCE"))
            .build();
    }
}
