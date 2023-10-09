package com.stopnewsletter.backend.catalog.source.dto;

import com.stopnewsletter.backend.source.SourceType;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.UUID;

public interface SourceDto {

    UUID getId();
    SourceType getType();
    String getUrl();
    String getName();
    Date getUpdate();
    boolean isActive();

    static Proxy create() {
        return new Proxy();
    }

    @Setter
    @Accessors( fluent= true, chain= true)
    class Proxy implements SourceDto {

        private UUID id;
        private SourceType type;
        private String url;
        private String name;
        private Date update;
        private boolean active;

        @Override public UUID getId() {
            return id;
        }
        @Override public SourceType getType() {
            return type;
        }
        @Override public String getUrl() {
            return url;
        }
        @Override public String getName() {
            return name;
        }

        @Override public Date getUpdate() {
            return update;
        }

        @Override public boolean isActive() {
            return active;
        }
    }
}
