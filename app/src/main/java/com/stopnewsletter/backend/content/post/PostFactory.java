package com.stopnewsletter.backend.content.post;

import com.stopnewsletter.backend.blog.*;
import com.stopnewsletter.backend.content.Creator;
import com.stopnewsletter.backend.content.CreatorRepository;
import com.stopnewsletter.backend.content.post.dto.BlogAuthorDto;
import com.stopnewsletter.backend.content.post.dto.BlogCategoryDto;
import com.stopnewsletter.backend.content.post.dto.PostDto;
import com.stopnewsletter.backend.source.SourceRepository;
import lombok.AllArgsConstructor;

import java.util.stream.Collectors;

@AllArgsConstructor
public class PostFactory {

    private SourceRepository blogs;
    private BlogCategoryRepository categories;
    private CreatorRepository creators;
    private BlogAuthorRepository authors;

    Post from( PostDto source) {
        return update( new Post(), source);
    }
    BlogCategory from( BlogCategoryDto source) {
        var categoryId= new BlogCategoryId( source.getId(), source.getBlogId());
        return categories.findById( categoryId)
                .orElse( categories.save( new BlogCategory()
                        .name( source.getName())
                        .blogCategoryId( categoryId)));
    }
    BlogAuthor from( BlogAuthorDto source) {

        // Są cztery przypadki:
        // 1. jest autor i jest twórca.
        // 2. jest autor, nie ma twórcy <- teoretycznie tak nie powinno być,
        // 3. nie ma autora, jest twórca,
        // 4. nie ma autora, nie ma twórcy,

        return authors.findById( new BlogAuthorId( source.getId(), source.getBlogId()))
                .map( blogAuthor->{
                    //blogAuthor.creator( creators.findByName( source.getName())            // 1
                     //       .orElse( creators.save( new Creator().name( source.getName()))));   // 2
                    return blogAuthor;
                }).orElseGet( ()->{
                    return authors.save( new BlogAuthor()
                            .name( source.getName())
                            .blogAuthorId( new BlogAuthorId( source.getId(), source.getBlogId()))
                            .creator( creators.findByName( source.getName())                  // 3
                                    .orElse( creators.save( new Creator().name( source.getName()))))); // 4
                });
    }
     private Post update( Post post, PostDto source){
        return (Post) post
                .setCategories( source.getCategories().stream()
                                .map( this::from).collect( Collectors.toSet()))
                .setCreators( source.getAuthors().stream()
                                .map( this::from).collect( Collectors.toSet()))
                .setPostId( source.getId())
                //.setSource( blogs.findById( source.getBlog().getId()).get())
                .setTitle( source.getTitle())
                .setDate( source.getDate());
    }
}
