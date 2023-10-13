package com.stopnewsletter.backend.content.post;

import com.stopnewsletter.backend.content.post.dto.BlogTagDto;

import java.util.Optional;
import java.util.UUID;

public interface BlogTagQueryRepository {

    Optional<BlogTagDto> findByIdAndBlogId(int id, UUID blogId);
}
