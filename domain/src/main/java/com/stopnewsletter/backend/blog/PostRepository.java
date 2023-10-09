package com.stopnewsletter.backend.blog;

import com.stopnewsletter.backend.content.Content;

public interface PostRepository {
    public Content save(Content post);
}
