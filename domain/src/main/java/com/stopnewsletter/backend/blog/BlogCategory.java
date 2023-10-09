package com.stopnewsletter.backend.blog;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.UUID;

@Getter
@Entity
@Table( name= "BLOG_CATEGORIES")
@IdClass( BlogCategoryId.class)
public class BlogCategory {

    @Id
    private int id;

    @Id
    @Column( name= "BLOG_ID")
    private UUID blogId;

    private String name;

    public BlogCategory name( String name) {
        this.name= name;
        return this;
    }

    public BlogCategory blogCategoryId ( BlogCategoryId categoryId) {
        this.id= categoryId.getId();
        this.blogId= categoryId.getBlogId();
        return this;
    }
}
