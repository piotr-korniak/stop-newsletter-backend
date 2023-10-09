package com.stopnewsletter.backend.catalog.source;

import com.stopnewsletter.backend.blog.Blog;
import com.stopnewsletter.backend.source.Source;
import com.stopnewsletter.backend.source.SourceType.SourceTypeVisitor;
import com.stopnewsletter.backend.catalog.source.dto.SourceDto;
import com.stopnewsletter.backend.source.SourceYt;

public class SourceFactory implements SourceTypeVisitor<Source> {

    Source from( SourceDto source) {
        return update( source.getType().accept( this), source);
    }

    Source update( Source source, SourceDto dto) {
        return source
                .setId( dto.getId())
                .setName( dto.getName())
                .setUrl( dto.getUrl())
                .setUpdate( dto.getUpdate())
                .setActive( dto.isActive());
    }

    @Override public Source visitSt() {
        return null;
    }

    @Override public Source visitUd() {
        return null;
    }

    @Override public Source visitWp() {
        return new Blog();
    }

    @Override public Source visitYt() {
        return new SourceYt();
    }

}
