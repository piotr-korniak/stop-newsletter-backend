package com.stopnewsletter.backend.wordpress.jax;

import lombok.Getter;

@Getter
public class WordPressAttribute {

    private int id;
    private String name;

    public void setId( int id){
        this.id= id;
    }

    public void setName( String name){
        this.name= name;
    }
}
