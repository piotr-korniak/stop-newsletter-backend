package com.stopnewsletter.backend.content;

import java.util.Optional;

public interface CreatorRepository {

    Optional<Creator> findByName( String name);
    Creator save( Creator creator);
}
