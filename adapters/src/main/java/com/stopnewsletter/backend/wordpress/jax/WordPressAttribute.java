package com.stopnewsletter.backend.wordpress.jax;

import lombok.Getter;

@Getter
public class WordPressAttribute {

    private int id;
    private String name;

    public WordPressAttribute setId( int id){
        this.id= id;
        return this;
    }

    public WordPressAttribute setName( String name){
        this.name= name;
        return this;
    }
}
