package com.stopnewsletter.backend.blog;

import java.util.Optional;

public interface BlogCategoryRepository {

    BlogCategory save( BlogCategory blogCategory);
    Optional<BlogCategory> findById( BlogAttributeId id);
}
