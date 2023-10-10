package com.stopnewsletter.backend.blog;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.UUID;

@Getter
@Entity
@Table( name= "BLOG_TAGS")
@IdClass( BlogAttributeId.class)
public class BlogTag {

    @Id
    private int id;

    @Id
    @Column(name = "BLOG_ID")
    private UUID blogId;

    private String name;

    public BlogTag name( String name) {
        this.name= name;
        return this;
    }

    public BlogTag blogTagId( BlogAttributeId tagId) {
        this.id= tagId.getId();
        this.blogId= tagId.getBlogId();
        return this;
    }
}
