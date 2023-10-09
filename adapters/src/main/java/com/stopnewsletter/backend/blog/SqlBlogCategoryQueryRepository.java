package com.stopnewsletter.backend.blog;

import com.stopnewsletter.backend.content.post.BlogCategoryQueryRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.Repository;

import java.util.Optional;

interface SqlBlogCategoryRepository extends JpaRepository<BlogCategory, BlogCategoryId> {

}

@org.springframework.stereotype.Repository
interface SqlBlogCategoryQueryRepository extends BlogCategoryQueryRepository,
                                                    Repository<BlogCategory, BlogCategoryId> {
}

@org.springframework.stereotype.Repository
@AllArgsConstructor
class BlogCategoryRepositoryImpl implements BlogCategoryRepository {

    private final SqlBlogCategoryRepository repository;
    @Override public BlogCategory save( BlogCategory category) {
        return repository.save( category);
    }

    @Override public Optional<BlogCategory> findById( BlogCategoryId id) {
        return repository.findById( id);
    }
}
