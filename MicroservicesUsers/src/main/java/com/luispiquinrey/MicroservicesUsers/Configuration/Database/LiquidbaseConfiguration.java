package com.luispiquinrey.MicroservicesUsers.Configuration.Database;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.zaxxer.hikari.HikariDataSource;

import liquibase.integration.spring.SpringLiquibase;

@Configuration
public class LiquidbaseConfiguration {

    @Primary
    @Bean
    @ConfigurationProperties("spring.datasource.primary")
    public HikariDataSource primaryDatasource() {
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }

    @Bean
    @ConfigurationProperties("spring.datasource.primary.liquibase")
    public LiquibaseProperties primaryLiquibaseProperties() {
        return new LiquibaseProperties();
    }

    @Primary
    @Bean
    public SpringLiquibase primaryLiquibase() {
        return createSpringLiquibase(primaryDatasource(), primaryLiquibaseProperties());
    }

    @Bean
    @ConfigurationProperties("spring.datasource.secondary")
    public HikariDataSource secondaryDataSource() {
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }

    @Bean
    @ConfigurationProperties("spring.datasource.secondary.liquibase")
    public LiquibaseProperties secondaryLiquibaseProperties() {
        return new LiquibaseProperties();
    }

    @Bean
    public SpringLiquibase secondaryLiquibase() {
        return createSpringLiquibase(secondaryDataSource(), secondaryLiquibaseProperties());
    }

    private SpringLiquibase createSpringLiquibase(DataSource dataSource, LiquibaseProperties props) {
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setDataSource(dataSource);
        liquibase.setChangeLog(props.getChangeLog());



        liquibase.setDefaultSchema(props.getDefaultSchema());
        liquibase.setDropFirst(props.isDropFirst());
        liquibase.setShouldRun(props.isEnabled());

        liquibase.setChangeLogParameters(props.getParameters());
        liquibase.setRollbackFile(props.getRollbackFile());
        return liquibase;
    }
}
