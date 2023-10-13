package com.stopnewsletter.backend.wordpress.jax;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;

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
    private String excerpt;
    private WordPressAttribute author= new WordPressAttribute();
    private Date date;
    private int media;

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

    @JsonProperty( "yoast_head_json")
    public void setYoast( Object map) throws JsonProcessingException {
        var mapper= new ObjectMapper();
        var yoast= mapper.readValue( mapper.writeValueAsString( map), YoastHeadJson.class);
        this.author.setName( yoast.getAuthor());
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

    public void setTitle( HashMap<String, String> map) {
        if( map.containsKey( "rendered"))
            this.title= map.get( "rendered");
    }

    public void setExcerpt( HashMap<String, String> map) {
        if( map.containsKey( "rendered"))
            this.excerpt= map.get( "rendered");
    }

    public void setFeatured_media( int media) {
        this.media= media;
    }


    public void setAuthor( int author) {
        this.author.setId( author);
    }

    @Setter
    @Getter
    @JsonIgnoreProperties( ignoreUnknown= true)
    public static class YoastHeadJson {
        private String author;

    }
}
