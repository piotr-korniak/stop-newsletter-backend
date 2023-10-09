package com.stopnewsletter.backend.notion.dto;

import com.stopnewsletter.backend.source.SourceType;

import java.util.Map;
import java.util.UUID;

public class NotionSource {
    private Map<String, NotionProperty> properties;

    public void setProperties( Map<String, NotionProperty> properties) {
        this.properties= properties;
    }
    public UUID getId() {
        return UUID.fromString( properties.get( "id").getValue()
                    .replaceFirst(
                        "(\\p{XDigit}{8})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}+)", "$1-$2-$3-$4-$5"
                    ));
    }

    public SourceType getType() {
        return SourceType.valueOf(
                properties.get( "type").getValue());
    }

    public String getUrl() {
        return properties.get( "url").getValue();
    }

    public String getName() {
        return properties.get( "name").getValue();
    }

    public boolean getActive() {
        return properties.get( "active").isCheckbox();
    }

}
