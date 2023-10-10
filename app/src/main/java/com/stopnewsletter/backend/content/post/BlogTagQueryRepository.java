package com.stopnewsletter.backend.content.post;

import com.stopnewsletter.backend.content.post.dto.BlogAttributeDto;

import java.util.Optional;
import java.util.UUID;

public interface BlogTagQueryRepository {

    Optional<BlogAttributeDto> findByIdAndBlogId( int id, UUID blogId);
}
