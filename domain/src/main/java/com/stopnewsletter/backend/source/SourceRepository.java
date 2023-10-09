package com.stopnewsletter.backend.source;

import java.util.Optional;
import java.util.UUID;

public interface SourceRepository {

    Source save( Source source);

    Optional<Source> findById( UUID id);

}
