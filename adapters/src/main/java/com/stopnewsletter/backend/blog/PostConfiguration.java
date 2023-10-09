package com.stopnewsletter.backend.blog;

import com.stopnewsletter.backend.content.CreatorRepository;
import com.stopnewsletter.backend.content.post.PostFacade;
import com.stopnewsletter.backend.content.post.PostFactory;
import com.stopnewsletter.backend.source.SourceRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PostConfiguration {

    @Bean
    PostFacade postFacade(final PostRepository repository,
                          final BlogCategoryRepository blogCategories,
                          final SourceRepository blogs,
                          final CreatorRepository creators,
                          final BlogAuthorRepository blogAuthors) {
        return new PostFacade( new PostFactory( blogs, blogCategories, creators, blogAuthors), repository);
    }
}
