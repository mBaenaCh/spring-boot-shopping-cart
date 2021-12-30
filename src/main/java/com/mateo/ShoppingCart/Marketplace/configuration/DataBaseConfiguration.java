package com.mateo.ShoppingCart.Marketplace.configuration;

import org.springframework.context.annotation.Configuration;

@Configuration
public class DataBaseConfiguration {

    @Bean
    public DataSource dataSource() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:postgresql://localhost:5432/postgres");
        config.setUsername("postgres");
        config.setPassword("postgres");
        return new HikariDataSource(config);
    }

    @Bean
    public JdbcTemplate jdbcTemplate(
            DataSource dataSource
    ) {
        return new JdbcTemplate(dataSource);
    }
}
