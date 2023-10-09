package com.stopnewsletter.backend.notion.dto;

import com.stopnewsletter.backend.notion.NotionType;
import lombok.Getter;

import java.util.UUID;

@Getter
public class NotionObject {

    private UUID id;
    private NotionType type;

    public void setId( UUID id) {
        this.id= id;
    }
    public void setType( String type) {
        this.type= NotionType.fromString( type);
    }


}
