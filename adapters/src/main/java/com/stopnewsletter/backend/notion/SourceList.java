package com.stopnewsletter.backend.notion;

import com.stopnewsletter.backend.notion.dto.NotionSource;

import java.util.List;

public class SourceList {

    List<NotionSource> results;

    public void setResults( List<NotionSource> results){
        this.results= results;
    }

    public List<NotionSource> getResult() {
        return results;
    }
}
