package com.stopnewsletter.backend.notion;

public enum NotionType {
    B( "block"),
    CD( "child_database"),
    P( "paragraph");

    private String name;

    NotionType( String name) {
        this.name= name;
    }
    private boolean isName( String name) {
        return this.name.equalsIgnoreCase( name);
    }

    public boolean isType( NotionType type) {
        return this.compareTo( type)== 0;
    }

    public static NotionType fromString( String name) {
        for( NotionType notionType: NotionType.values()) {
            if( notionType.isName( name)) {
                return notionType;
            }
        }
        return null;
    }
}
