package com.luispiquinrey.KnotCommerce.Configuration.OpenFeign;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import feign.RequestInterceptor;
import feign.auth.BasicAuthRequestInterceptor;

@Configuration
public class InterceptorOpenFeign{
    @Bean
    public RequestInterceptor basicAuthRequestInterceptor(){
        return new BasicAuthRequestInterceptor("LuisPiquiney","LuisPiquinRey");
    }
    
}
