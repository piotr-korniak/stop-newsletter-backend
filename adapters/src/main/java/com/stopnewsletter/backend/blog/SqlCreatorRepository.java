package com.stopnewsletter.backend.blog;

import com.stopnewsletter.backend.content.Creator;
import com.stopnewsletter.backend.content.CreatorRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface SqlCreatorRepository extends JpaRepository<Creator, UUID> {

    Optional<Creator> findByName( String name);
}

@AllArgsConstructor
@org.springframework.stereotype.Repository
class CreatorRepositoryImpl implements CreatorRepository {

    private final SqlCreatorRepository repository;

    @Override public Optional<Creator> findByName( String name) {
        return repository.findByName( name);
    }

    @Override public Creator save( Creator creator) {
        return repository.save( creator);
    }
}


