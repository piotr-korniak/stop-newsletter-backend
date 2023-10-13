package com.stopnewsletter.backend.blog;

import com.stopnewsletter.backend.content.Content;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table( name= "POSTS")
@PrimaryKeyJoinColumn( name= "CONTENT_ID", referencedColumnName= "ID")
@DiscriminatorValue( "WP")
public class Post extends Content {

    //@Embedded
   // private PostId postId;

    @Column( name = "ID")
    private int postId;

    @ManyToMany( cascade = {CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinTable( name= "POST_TAGS",
            joinColumns= @JoinColumn( name= "POST_ID", referencedColumnName= "CONTENT_ID"),
            inverseJoinColumns={ @JoinColumn( name= "TAG_ID", referencedColumnName= "ID"),
                    @JoinColumn( name= "BLOG_ID", referencedColumnName = "BLOG_ID")})
    private Set<BlogTag> tags= new HashSet<>();

    @ManyToMany( cascade = {CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinTable( name= "POST_CATEGORIES",
            joinColumns= @JoinColumn( name= "POST_ID", referencedColumnName= "CONTENT_ID"),
            inverseJoinColumns={ @JoinColumn( name= "CATEGORY_ID", referencedColumnName= "ID"),
                            @JoinColumn( name= "BLOG_ID", referencedColumnName = "BLOG_ID")})
    private Set<BlogCategory> categories= new HashSet<>();

    @ManyToMany( cascade = {CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinTable( name= "POST_AUTHORS",
            joinColumns= @JoinColumn( name= "POST_ID", referencedColumnName= "CONTENT_ID"),
            inverseJoinColumns={ @JoinColumn( name= "AUTHOR_ID", referencedColumnName= "ID"),
                    @JoinColumn( name= "BLOG_ID", referencedColumnName = "BLOG_ID")})
    private Set<BlogAuthor> authors= new HashSet<>();

    public Content setPostId( PostId postId) {
        setSource( postId.getBlogId());
        this.postId = postId.getId();
        return this;
    }

    public Post setTags( Set<BlogTag> tags) {
        this.tags= tags;
        return this;
    }

    public Post setCategories( Set<BlogCategory> categories) {
        this.categories= categories;
        return this;
    }

    public Post setAuthors(Set<BlogAuthor> authors) {
        this.authors= authors;
        setCreators( authors.stream()
                .map( author-> author.getCreator()).collect( Collectors.toSet()));
        return this;
    }


}
