package com.stopnewsletter.backend.blog;

import jakarta.persistence.Column;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.io.Serializable;
import java.util.UUID;

@Getter
@EqualsAndHashCode
public class BlogAttributeId implements Serializable {

    private int id;

    @Column( name= "BLOG_ID")
    private UUID blogId;

    public BlogAttributeId() {
    }

    public BlogAttributeId( int id, UUID blogId) {
        this.id= id;
        this.blogId= blogId;
    }

}
