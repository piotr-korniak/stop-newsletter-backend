package com.stopnewsletter.backend.database;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.SQLException;

@Configuration
public class MainSourceConfiguration {
    @Bean
    @ConfigurationProperties( "databases.main.datasource")
    public DataSourceProperties mainDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @LiquibaseDataSource   // for poolName and schema properties
    @ConfigurationProperties( "databases.main.datasource.hikari")
    public HikariDataSource mainDataSource() throws SQLException {
        return mainDataSourceProperties()
                .initializeDataSourceBuilder()
                .type( HikariDataSource.class)
                .build();
    }
}
