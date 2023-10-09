package com.stopnewsletter.backend.blog;

import com.stopnewsletter.backend.content.Content;
import com.stopnewsletter.backend.content.post.PostQueryRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.Repository;

import java.util.UUID;

public interface SqlPostRepository extends JpaRepository<Content, UUID> {
}

@org.springframework.stereotype.Repository
interface PostQueryRepositoryImpl extends PostQueryRepository,
                                            Repository<Post, UUID> {

}


@org.springframework.stereotype.Repository
@AllArgsConstructor
class PostRepositoryImpl implements PostRepository {

    private final SqlPostRepository repository;

    @Override public Content save(Content post) {
        return repository.save( post);
    }
}
