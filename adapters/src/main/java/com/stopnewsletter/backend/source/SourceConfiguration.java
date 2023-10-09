package com.stopnewsletter.backend.source;

import com.stopnewsletter.backend.catalog.source.SourceFacade;
import com.stopnewsletter.backend.catalog.source.SourceFactory;
import com.stopnewsletter.backend.content.post.PostFacade;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SourceConfiguration {

    @Bean
    SourceFacade sourceFacade( final SourceRepository repository,
                               final PostFacade post) {
        return new SourceFacade( new SourceFactory(), repository, post);
    }
}
