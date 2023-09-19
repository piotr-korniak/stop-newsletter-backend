package com.stopnewsletter.backend.scene;

import com.stopnewsletter.backend.database.LiquibaseConfig;
import liquibase.exception.LiquibaseException;
import liquibase.integration.spring.SpringLiquibase;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.core.io.ResourceLoader;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.StatementCallback;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Service
@EnableConfigurationProperties( LiquibaseProperties.class)
public class SceneService {

    private final SqlSceneRepository repository;

    private final ResourceLoader resourceLoader;
    private final JdbcTemplate jdbcTemplate;
    private final LiquibaseProperties liquibaseProperties;

    @Value("${databases.main.datasource.username}")
    private String username;

    @Value("${databases.main.datasource.password}")
    private String password;

    @Value("${databases.scene.datasource.url-prefix}")
    private String urlPrefix;

    public SceneService( SqlSceneRepository repository,
                         ResourceLoader resourceLoader,
                         JdbcTemplate jdbcTemplate,
                         @Qualifier("sceneLiquibaseProperties")
                         LiquibaseProperties liquibaseProperties) {
        this.repository= repository;
        this.resourceLoader= resourceLoader;
        this.jdbcTemplate= jdbcTemplate;
        this.liquibaseProperties= liquibaseProperties;
    }

    public void createScene( String code, String name) {
        if( repository.existsByCode( code))
            return;
        System.err.println("Nie ma... działamy!");

        // utworzenie bazy danych

        jdbcTemplate.execute(
                (StatementCallback<Boolean>) stmt -> stmt.execute("CREATE DATABASE " + code));
        jdbcTemplate.execute((StatementCallback<Boolean>) stmt -> stmt
                .execute("GRANT ALL PRIVILEGES ON DATABASE " + code + " TO " + username));

        try (Connection connection = DriverManager.getConnection(urlPrefix + code, username, password)) {
            runLiquibase( new SingleConnectionDataSource( connection, false));
        } catch (SQLException | LiquibaseException e) {
            throw new RuntimeException( "Error when populating db: ", e);
        }

        repository.save( new Scene()
                .setCode( code)
                .setName( name));
    }

    private void runLiquibase( DataSource dataSource) throws LiquibaseException {
        SpringLiquibase springLiquibase= LiquibaseConfig.getSpringLiquibase( liquibaseProperties);
        springLiquibase.setDataSource( dataSource);
        springLiquibase.setResourceLoader( resourceLoader);
        springLiquibase.afterPropertiesSet();
    }
}
