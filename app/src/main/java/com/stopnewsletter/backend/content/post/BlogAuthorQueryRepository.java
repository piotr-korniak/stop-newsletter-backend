package com.stopnewsletter.backend.content.post;

import com.stopnewsletter.backend.content.post.dto.BlogAuthorDto;

import java.util.Optional;
import java.util.UUID;

public interface BlogAuthorQueryRepository {
    Optional<BlogAuthorDto> findByIdAndBlogId( int author, UUID blogId);
}
