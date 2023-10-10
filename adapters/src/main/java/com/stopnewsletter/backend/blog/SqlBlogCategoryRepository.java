package com.stopnewsletter.backend.blog;

import com.stopnewsletter.backend.content.post.BlogCategoryQueryRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.Repository;

import java.util.Optional;

interface SqlBlogCategoryRepository extends JpaRepository<BlogCategory, BlogAttributeId> {
}

interface SqlBlogCategoryQueryRepository extends BlogCategoryQueryRepository,
        Repository<BlogCategory, BlogAttributeId> {
}

@AllArgsConstructor
@org.springframework.stereotype.Repository
class BlogCategoryRepositoryImpl implements BlogCategoryRepository {

    private final SqlBlogCategoryRepository repository;

    @Override public Optional<BlogCategory> findById(BlogAttributeId id) {
        return repository.findById( id);
    }

    @Override public BlogCategory save( BlogCategory category) {
        return repository.save( category);
    }
}