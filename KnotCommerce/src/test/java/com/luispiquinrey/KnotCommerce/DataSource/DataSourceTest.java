package com.luispiquinrey.KnotCommerce.DataSource;

import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.MySQLContainer;

@SpringBootTest(classes = {DataSourceTest.DataSourceConfig.class})
public class DataSourceTest {

    static MySQLContainer<?> mysqlContainer = new MySQLContainer<>("mysql:8.0.32")
            .withDatabaseName("knotcommerce");

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeAll
    public static void setUp() {
        mysqlContainer.start();
        Runtime.getRuntime().addShutdownHook(new Thread(mysqlContainer::stop));
        System.setProperty("DB_URL", mysqlContainer.getJdbcUrl());
        System.setProperty("DB_USERNAME", mysqlContainer.getUsername());
        System.setProperty("DB_PASSWORD", mysqlContainer.getPassword());
    }

    @Test
    public void testMySQLConnection() {
        String query = "SELECT 1";
        Integer result = jdbcTemplate.queryForObject(query, Integer.class);
        Assertions.assertEquals(Integer.valueOf(1), result);
    }

    @Configuration
    static class DataSourceConfig {

        @Bean
        public DataSource dataSource() {
            HikariConfig hikariConfig = new HikariConfig();
            hikariConfig.setJdbcUrl(mysqlContainer.getJdbcUrl());
            hikariConfig.setUsername(mysqlContainer.getUsername());
            hikariConfig.setPassword(mysqlContainer.getPassword());
            return new HikariDataSource(hikariConfig);
        }

        @Bean
        public JdbcTemplate jdbcTemplate(DataSource dataSource) {
            return new JdbcTemplate(dataSource);
        }
    }
}
