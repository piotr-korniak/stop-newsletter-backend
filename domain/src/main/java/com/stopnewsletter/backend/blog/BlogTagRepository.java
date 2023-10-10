package com.stopnewsletter.backend.blog;

import java.util.Optional;

public interface BlogTagRepository {
    Optional<BlogTag> findById( BlogAttributeId categoryId);

    BlogTag save( BlogTag blogTag);
}
