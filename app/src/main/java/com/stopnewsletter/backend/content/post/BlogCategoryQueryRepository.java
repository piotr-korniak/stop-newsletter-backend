package com.stopnewsletter.backend.content.post;

import com.stopnewsletter.backend.content.post.dto.BlogCategoryDto;

import java.util.Optional;
import java.util.UUID;

public interface BlogCategoryQueryRepository {

    Optional<BlogCategoryDto> findByIdAndBlogId( int id, UUID blogId);

}
