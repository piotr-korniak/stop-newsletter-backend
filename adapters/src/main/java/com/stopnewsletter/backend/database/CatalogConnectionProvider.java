package com.stopnewsletter.backend.database;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import com.github.benmanes.caffeine.cache.RemovalCause;
import com.zaxxer.hikari.HikariDataSource;
import jakarta.annotation.PostConstruct;
import org.hibernate.engine.jdbc.connections.spi.AbstractDataSourceBasedMultiTenantConnectionProviderImpl;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.concurrent.TimeUnit;

@Component
public class CatalogConnectionProvider
        extends AbstractDataSourceBasedMultiTenantConnectionProviderImpl {

    private LoadingCache<String, HikariDataSource> sceneDataSources;
    private final DataSourceProperties dataSourceProperties;

    @Qualifier( "mainDataSource")
    private final DataSource mainDataSource;

    @Value( "${databases.scene.datasource.url-prefix}")
    private String urlPrefix;

    @Value( "${databases.scene.cache.maximumSize:10}")
    private Long maximumSize;

    @Value( "${databases.scene.cache.expireAfterAccess:10}")
    private Integer expireAfterAccess;

    public CatalogConnectionProvider( @Qualifier( "mainDataSource")
                                      DataSource mainDataSource,
                                      @Qualifier( "mainDataSourceProperties")
                                      DataSourceProperties dataSourceProperties) {
        this.mainDataSource= mainDataSource;
        this.dataSourceProperties= dataSourceProperties;
    }

    @PostConstruct
    private void createCache() {

        sceneDataSources= Caffeine.newBuilder()
                .maximumSize( maximumSize)
                .expireAfterAccess( expireAfterAccess, TimeUnit.MINUTES)
                .removalListener( (String key, HikariDataSource dataSource, RemovalCause cause)->
                        dataSource.close())
                .build( key-> dataSourceProperties
                        .initializeDataSourceBuilder()
                        .type( HikariDataSource.class)
                        .url( urlPrefix+ key)
                        .build()
                );
    }

    @Override
    protected DataSource selectAnyDataSource() {
        return mainDataSource;
    }

    @Override
    protected DataSource selectDataSource( String tenantIdentifier) {
        return sceneDataSources.get( tenantIdentifier);
    }
/*
    @Override
    public void releaseAnyConnection( Connection connection) throws SQLException {
        mainDataSource.getConnection().close();
    }*/
/*
    @Override
    public void releaseConnection( String tenantIdentifier, Connection connection) throws SQLException {
        sceneDataSources.invalidate( tenantIdentifier);
    }*/
}
