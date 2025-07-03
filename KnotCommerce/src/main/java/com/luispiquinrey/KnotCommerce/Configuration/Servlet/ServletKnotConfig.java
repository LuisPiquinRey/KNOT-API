package com.luispiquinrey.KnotCommerce.Configuration.Servlet;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServletKnotConfig {

    @Bean
    public ServletRegistrationBean<ServletKnot> servletKnot() {
        ServletRegistrationBean<ServletKnot> bean = new ServletRegistrationBean<>(new ServletKnot(), "/servletKnot");
        bean.setLoadOnStartup(1);
        return bean;
    }
}
