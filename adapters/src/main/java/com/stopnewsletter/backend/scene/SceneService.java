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

    public boolean existsByCode( String code) {
        return repository.existsByCode( code);
    }

    public Scene createScenes( Scene scene) {
        System.err.println( "createScenes: "+ scene.getCode());
        jdbcTemplate.execute(
                (StatementCallback<Boolean>) stmt -> stmt.execute("CREATE DATABASE "+ scene.getCode()));
        jdbcTemplate.execute((StatementCallback<Boolean>) stmt -> stmt
                .execute("GRANT ALL PRIVILEGES ON DATABASE "+ scene.getCode()+ " TO " + username));

        try( Connection connection= DriverManager.getConnection(urlPrefix+ scene.getCode(), username, password)) {
            runLiquibase( new SingleConnectionDataSource(connection, false));
        } catch ( SQLException | LiquibaseException e) {
            throw new RuntimeException( "Error when populating db: ", e);
        }
        return repository.save( scene);
    }

    private void runLiquibase( DataSource dataSource) throws LiquibaseException {
        SpringLiquibase springLiquibase= LiquibaseConfig.getSpringLiquibase( liquibaseProperties);
        springLiquibase.setDataSource( dataSource);
        springLiquibase.setResourceLoader( resourceLoader);
        springLiquibase.afterPropertiesSet();
    }

}
