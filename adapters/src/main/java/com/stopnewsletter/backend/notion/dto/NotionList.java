package com.stopnewsletter.backend.notion.dto;

import java.util.List;

public class NotionList {

    List<NotionObject> results;

    public void setResults( List<NotionObject> results){
        this.results= results;
    }

    public List<NotionObject> getResult() {
        return results;
    }
}
