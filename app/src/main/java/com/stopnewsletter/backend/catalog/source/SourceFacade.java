package com.stopnewsletter.backend.catalog.source;

import com.stopnewsletter.backend.catalog.source.dto.SourceDto;
import com.stopnewsletter.backend.content.post.PostFacade;
import com.stopnewsletter.backend.content.post.dto.PostDto;
import com.stopnewsletter.backend.source.Source;
import com.stopnewsletter.backend.source.SourceRepository;
import lombok.AllArgsConstructor;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
public class SourceFacade {

    SourceFactory source;
    SourceRepository sources;

    PostFacade post;

    public Source save( SourceDto sourceDto) {
        return sources.save( sources.findById( sourceDto.getId())
                .map( source-> this.source.from( sourceDto)
                        .setUpdate( source.getUpdate()))    // save update
                .orElse( this.source.from( sourceDto)));
    }

    public void update( UUID sourceId, Date update) {
        sources.findById( sourceId)
                .ifPresent( source-> sources.save( source).setUpdate( update));
    }

    public void addPost( PostDto postDto) {
        sources.findById( postDto.getBlog().getId())
            .ifPresent( blog-> {
                post.save( blog, postDto);

                if( Optional.ofNullable( blog.getUpdate()).isEmpty() ||
                        postDto.getDate().after( blog.getUpdate()))     // update
                    sources.save( blog.setUpdate( postDto.getDate()));
            });
    }
}
