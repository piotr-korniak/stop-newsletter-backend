package com.stopnewsletter.backend.blog;

import com.stopnewsletter.backend.content.Content;
import com.stopnewsletter.backend.content.post.PostQueryRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.Repository;

import java.util.UUID;

interface SqlPostRepository extends JpaRepository<Content, UUID> {
}

interface SqlPostQueryRepository extends PostQueryRepository,
                                            Repository<Post, UUID> {
}

@AllArgsConstructor
@org.springframework.stereotype.Repository
class PostRepositoryImpl implements PostRepository {

    private final SqlPostRepository repository;

    @Override public Content save(Content post) {
        return repository.save( post);
    }
}
