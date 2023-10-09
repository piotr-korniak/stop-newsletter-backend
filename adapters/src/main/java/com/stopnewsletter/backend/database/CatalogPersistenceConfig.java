package com.stopnewsletter.backend.database;

import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.hibernate5.SpringBeanContainer;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import java.util.Map;

@Configuration
@EnableJpaRepositories(
       /* basePackages= { "${databases.catalog.repositories.post}",
                        "${databases.catalog.repositories.source}"},*/
        basePackages= { "com.stopnewsletter.backend.blog",
                        "com.stopnewsletter.backend.source"},
        entityManagerFactoryRef= "catalogEntityManagerFactory",
        transactionManagerRef= "catalogTransactionManager"
)
@RequiredArgsConstructor
@EnableConfigurationProperties( JpaProperties.class)
public class CatalogPersistenceConfig {

    private final ConfigurableListableBeanFactory beanFactory;
    private final JpaProperties jpaProperties;

    @Value( "${databases.catalog.packages}")
    private String[] entityPackages;
    @Bean
    public JpaTransactionManager catalogTransactionManager( @Qualifier( "catalogEntityManagerFactory")
                                                            EntityManagerFactory emf) {
        return new JpaTransactionManager( emf);
    }
    @Bean
    public LocalContainerEntityManagerFactoryBean catalogEntityManagerFactory(
            @Qualifier( "catalogConnectionProvider") MultiTenantConnectionProvider connectionProvider,
            @Qualifier( "catalogIdentifierResolver") CurrentTenantIdentifierResolver tenantResolver) {

        LocalContainerEntityManagerFactoryBean emf= new LocalContainerEntityManagerFactoryBean();

        emf.setPersistenceUnitName( "catalog-persistence-unit");
        emf.setPackagesToScan( entityPackages);

        JpaVendorAdapter vendorAdapter= new HibernateJpaVendorAdapter();
        emf.setJpaVendorAdapter( vendorAdapter);

        emf.setJpaPropertyMap( jpaProperties.getProperties());
        emf.setJpaPropertyMap( Map.of(
                AvailableSettings.PHYSICAL_NAMING_STRATEGY,
                "org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy",
                AvailableSettings.IMPLICIT_NAMING_STRATEGY,
                "org.springframework.boot.orm.jpa.hibernate.SpringImplicitNamingStrategy",
                AvailableSettings.BEAN_CONTAINER,
                new SpringBeanContainer( this.beanFactory),
                AvailableSettings.MULTI_TENANT_CONNECTION_PROVIDER,
                connectionProvider,
                AvailableSettings.MULTI_TENANT_IDENTIFIER_RESOLVER,
                tenantResolver));

        return emf;
    }
}
