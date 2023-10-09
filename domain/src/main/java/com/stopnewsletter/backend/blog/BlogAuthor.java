package com.stopnewsletter.backend.blog;

import com.stopnewsletter.backend.content.Creator;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.UUID;

@Getter
@Entity
@Table( name= "BLOG_AUTHORS")
@IdClass( BlogAuthorId.class)
public class BlogAuthor {

    @Id
    private int id;

    @Id
    @Column( name= "BLOG_ID")
    private UUID blogId;

    @ManyToOne
    private Creator creator;
    private String name;

    public BlogAuthor name( String name) {
        this.name= name;
        return this;
    }

    public BlogAuthor blogAuthorId ( BlogAuthorId authorId) {
        this.id= authorId.getId();
        this.blogId= authorId.getBlogId();
        return this;
    }

    public BlogAuthor creator( Creator creator) {
        this.creator= creator;
        return this;
    }
}
