package com.stopnewsletter.backend.database;

import org.checkerframework.checker.initialization.qual.Initialized;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.UnknownKeyFor;
import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component( "catalogIdentifierResolver")
public class CatalogIdentifierResolver implements CurrentTenantIdentifierResolver {
    @Override
    public @UnknownKeyFor @NonNull @Initialized String resolveCurrentTenantIdentifier() {
        System.err.println( "TenantContext: "+ TenantContext.getCatalogId());

        return Optional.ofNullable( TenantContext.getCatalogId())
                .map( String::valueOf)
                .orElse( "BOOTSTRAP");
    }

    @Override
    public @UnknownKeyFor @NonNull @Initialized boolean validateExistingCurrentSessions() {
        return true;
    }
}
