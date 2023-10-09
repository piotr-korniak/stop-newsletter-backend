package com.stopnewsletter.backend.content.post;

import java.util.UUID;

public interface PostQueryRepository {

    boolean existsBySourceIdAndPostId( UUID sourceId, int postId);

}
