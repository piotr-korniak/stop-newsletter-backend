package com.stopnewsletter.backend.blog;

import java.util.Optional;

public interface BlogAuthorRepository {
    Optional<BlogAuthor> findById( BlogAuthorId id);

    BlogAuthor save( BlogAuthor author);
}
