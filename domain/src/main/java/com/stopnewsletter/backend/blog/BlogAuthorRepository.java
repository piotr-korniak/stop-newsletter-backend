package com.stopnewsletter.backend.blog;

import java.util.Optional;

public interface BlogAuthorRepository {
    Optional<BlogAuthor> findById( BlogAttributeId id);

    BlogAuthor save( BlogAuthor author);
}
