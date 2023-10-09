package com.stopnewsletter.backend.database;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class TenantContext {

    private String tenant;
    private String catalog;
    private TenantContext() {}
    private static final InheritableThreadLocal<TenantContext> currentTenant= new InheritableThreadLocal<>(){
        @Override protected TenantContext initialValue() {
            return new TenantContext();
        }};

    public static void setCatalogId( String catalog) {
        log.debug( "Setting shardId: "+ catalog);
        currentTenant.get()
                .setCatalog( catalog)
                .setTenant( null);
    }

    public static void setTenantId(  String catalog, String tenant) {
        log.debug( "Setting shardId: "+ catalog+ " and tenantId: "+ tenant);
        currentTenant.get()
                .setCatalog( catalog)
                .setTenant( tenant);
    }

    private TenantContext setCatalog( String catalog) {
        this.catalog= catalog;
        return this;
    }

    private TenantContext setTenant( String tenant) {
        this.tenant= tenant;
        return this;
    }

    public static String getTenantId() {
        return currentTenant.get()!= null? currentTenant.get().tenant: null;
    }
    public static String getCatalogId() {
        return currentTenant.get()!= null? currentTenant.get().catalog : null;
    }

    public static void clear(){
        currentTenant.remove();
    }


}
