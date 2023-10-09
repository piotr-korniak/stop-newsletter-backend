package com.stopnewsletter.backend.notion.dto;

import lombok.Getter;

import java.util.List;
import java.util.Map;

@Getter
public class NotionProperty {

    private String value;
    private boolean checkbox;

    public void setFormula( Map<String, String> formula) {   // id
        this.value= formula.get( "string");
    }

    public void setSelect( Map<String, String> select) {     // typ
        this.value= select.get( "name");
    }

    public void setTitle( List<NotionText> title) {
        if( !title.isEmpty())
            this.value= title.get( 0).getPlainText();
    }

    public void setRich_text( List<NotionText> text) {
        if( !text.isEmpty())
            this.value= text.get( 0).getPlainText();
    }

    public void setCheckbox( boolean checkbox) {
        this.checkbox= checkbox;
    }

}
