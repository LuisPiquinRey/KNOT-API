package com.luispiquinrey.MicroservicesUsers.Configuration;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import jakarta.persistence.EntityManagerFactory;

@Configuration
public class DataSourceConfig {
    @Bean
    @ConfigurationProperties("app.datasource.sha1")
    public DataSourceProperties sha1Properties(){
        return new DataSourceProperties();
    }
    @Bean
    @ConfigurationProperties("app.datasource.sha2")
    public DataSourceProperties sha2Properties() {
        return new DataSourceProperties();
    }

    @Bean(name = "sha1DataSource")
    public DataSource sha1DataSource() {
        return sha1Properties().initializeDataSourceBuilder().build();
    }

    @Bean(name = "sha2DataSource")
    public DataSource sha2DataSource() {
        return sha2Properties().initializeDataSourceBuilder().build();
    }
    
    @Bean(name = "transactionManager")
    @Primary
    public PlatformTransactionManager transictionManager(EntityManagerFactory entityManagerFactory){
        return new JpaTransactionManager(entityManagerFactory);
    }
    @Bean(name = "dataSource")
    @Primary
    public DataSource routingDataSource(){
        Map<Object,Object> dataSources=new HashMap<>();
        dataSources.put("sha1", sha1DataSource());
        dataSources.put("sha2",sha2DataSource());
        ShardRoutingDataSource routingDataSource=new ShardRoutingDataSource();
        routingDataSource.setTargetDataSources(dataSources);
        routingDataSource.setDefaultTargetDataSource(sha1DataSource());
        routingDataSource.afterPropertiesSet();
        return routingDataSource;
    }
    @Bean(name = "entityManagerFactory")
    @Primary
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(EntityManagerFactoryBuilder builder){
        return builder
            .dataSource(routingDataSource())
            .packages("com.luispiquinrey.MicroservicesUsers.Entities")
            .persistenceUnit("sharedPersistenceUnit")
            .build();
    }
}