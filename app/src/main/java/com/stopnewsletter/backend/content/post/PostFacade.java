package com.stopnewsletter.backend.content.post;

import com.stopnewsletter.backend.blog.Post;
import com.stopnewsletter.backend.blog.PostRepository;
import com.stopnewsletter.backend.content.post.dto.PostDto;
import com.stopnewsletter.backend.source.Source;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PostFacade {

    PostFactory factory;
    PostRepository repository;

    public Post save( Source blog, PostDto source) {
        return (Post) repository.save( factory
                        .from( source)
                        .setSource( blog));
    }
}
