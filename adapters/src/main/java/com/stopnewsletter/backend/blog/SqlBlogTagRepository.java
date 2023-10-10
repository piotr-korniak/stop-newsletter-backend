package com.stopnewsletter.backend.blog;

import com.stopnewsletter.backend.content.post.BlogTagQueryRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.Repository;

import java.util.Optional;

public interface SqlBlogTagRepository extends JpaRepository<BlogTag, BlogAttributeId> {

}

interface SqlBlogTagQueryRepository extends BlogTagQueryRepository,
                                                Repository<BlogTag, BlogAttributeId> {
}

@org.springframework.stereotype.Repository
@AllArgsConstructor
class BlogTagRepositoryImpl implements BlogTagRepository {

    private final SqlBlogTagRepository repository;

    @Override public Optional<BlogTag> findById(BlogAttributeId id) {
        return repository.findById( id);
    }

    @Override public BlogTag save( BlogTag tag) {
        return repository.save( tag);
    }
}