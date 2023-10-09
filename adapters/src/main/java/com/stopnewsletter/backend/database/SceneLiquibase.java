package com.stopnewsletter.backend.database;

import com.stopnewsletter.backend.scene.Scene;
import com.stopnewsletter.backend.scene.SqlSceneRepository;
import liquibase.integration.spring.SpringLiquibase;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.core.io.ResourceLoader;


public class SceneLiquibase implements InitializingBean {

    @Autowired
    private SqlSceneRepository repository;

    @Autowired
    @Qualifier( "sceneLiquibaseProperties")
    private LiquibaseProperties sceneLiquibaseProperties;

    @Autowired
    private CatalogConnectionProvider sceneConnectionProvider;

    @Autowired
    private ResourceLoader resourceLoader;

    @Override
    public void afterPropertiesSet() throws Exception {
        SpringLiquibase springLiquibase= LiquibaseConfig
                .getSpringLiquibase( sceneLiquibaseProperties);
        springLiquibase.setResourceLoader( resourceLoader);

        for( Scene scene: repository.findAll()){
            springLiquibase.setDataSource( sceneConnectionProvider
                    .selectDataSource( scene.getCode()));
            springLiquibase.afterPropertiesSet();
        }
    }
}
