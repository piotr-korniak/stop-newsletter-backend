package com.stopnewsletter.backend.content.post;

import com.stopnewsletter.backend.blog.*;
import com.stopnewsletter.backend.content.Creator;
import com.stopnewsletter.backend.content.CreatorRepository;
import com.stopnewsletter.backend.content.post.dto.BlogTagDto;
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
    private BlogTagRepository tags;
    private CreatorRepository creators;
    private BlogAuthorRepository authors;

    Post from( PostDto source) {
        return update( new Post(), source);
    }
    BlogCategory from( BlogCategoryDto source) {
        var categoryId= new BlogAttributeId( source.getId(), source.getBlogId());
        return categories.findById( categoryId)
                .orElse( categories.save( new BlogCategory()
                        .name( source.getName())
                        .blogCategoryId( categoryId)));
    }

    BlogTag from( BlogTagDto source) {
        var tagId= new BlogAttributeId( source.getId(), source.getBlogId());
        return tags.findById( tagId)
                .orElse( tags.save( new BlogTag()
                        .name( source.getName())
                        .blogTagId( tagId)));
    }


    BlogAuthor from( BlogAuthorDto source) {
/**
        There are four cases:
        1. there is an author and there is a creator,
        2. there is an author, there is no creator (theoretically it shouldn't be like that),
        3. there is no author, there is a creator,
        4. there is no author, there is no creator.
*/
        return authors.findById( new BlogAttributeId( source.getId(), source.getBlogId()))
                .orElseGet( ()->{
                    return authors.save( new BlogAuthor()
                            .name( source.getName())
                            .blogAuthorId( new BlogAttributeId( source.getId(), source.getBlogId()))
                            .creator( creators.findByName( source.getName())                  // 3
                                    .orElse( creators.save( new Creator().name( source.getName()))))); // 4
                });
    }
     private Post update( Post post, PostDto source) {
         return (Post) post
                 .setTags(source.getTags().stream()
                         .map( this::from).collect(Collectors.toSet()))
                 .setCategories( source.getCategories().stream()
                          .map( this::from).collect( Collectors.toSet()))
                 .setAuthors( source.getAuthors().stream()
                         .map( this::from).collect(Collectors.toSet()))
                 .setPostId( source.getId())
                 .setTitle( source.getTitle())
                 .setDate( source.getDate());
     }
}

