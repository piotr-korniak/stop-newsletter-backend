package com.stopnewsletter.backend.blog;

import com.stopnewsletter.backend.content.post.BlogAuthorQueryRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.Repository;

import java.util.Optional;

interface SqlBlogAuthorRepository  extends JpaRepository<BlogAuthor, BlogAuthorId> {
}

@org.springframework.stereotype.Repository
interface SqlBlogAuthorQueryRepository extends BlogAuthorQueryRepository,
                                                Repository<BlogAuthor, BlogAuthorId> {
}

@org.springframework.stereotype.Repository
@AllArgsConstructor
class BlogAuthorRepositoryImpl implements BlogAuthorRepository {

    private final SqlBlogAuthorRepository repository;

    @Override public Optional<BlogAuthor> findById( BlogAuthorId id) {
        return repository.findById( id);
    }

    @Override public BlogAuthor save( BlogAuthor author) {
        return repository.save( author);
    }
}