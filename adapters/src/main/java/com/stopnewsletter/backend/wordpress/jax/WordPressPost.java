package com.stopnewsletter.backend.wordpress.jax;

import lombok.Getter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

@Getter
public class WordPressPost {

    private int id;

    private ArrayList<Integer> categories;
    private String link;
    private ArrayList<Integer> tags;
    private String title;
    private int author;
    private Date date;

    public static final SimpleDateFormat RRMDTX=
            new SimpleDateFormat( "yyyy-MM-dd'T'HH:mm:ss");

    public void setId( int id) {
        this.id= id;
    }

    public void setDate( String date) {
        try {
            this.date= RRMDTX.parse( date);
        } catch ( ParseException e) {
            throw new RuntimeException( e);
        }
    }

    public void setCategories( ArrayList<Integer> categories){
        this.categories= categories;
    }

    public void setLink( String link) {
        this.link= link;
    }

    public void setTags( ArrayList<Integer> tags){
        this.tags= tags;
    }

    public void setTitle( HashMap<String, String> title) {
        if( title.containsKey( "rendered"))
            this.title= title.get( "rendered");
    }

    public void setAuthor( int author) {
        this.author= author;
    }
}
