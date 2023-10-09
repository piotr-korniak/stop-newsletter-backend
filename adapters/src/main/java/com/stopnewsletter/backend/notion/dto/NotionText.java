package com.stopnewsletter.backend.notion.dto;

import lombok.Getter;

@Getter
public class NotionText {

    private String plainText;

    public void setPlain_text( String text) {
        this.plainText= text;
    }

}
