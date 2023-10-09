package com.stopnewsletter.backend.source;

import com.stopnewsletter.backend.catalog.source.SourceQueryRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.Repository;

import java.util.Optional;
import java.util.UUID;

public interface SqlSourceRepository extends JpaRepository<Source, UUID> {
}

@org.springframework.stereotype.Repository
interface SqlSourceQueryRepository extends SourceQueryRepository, Repository<Source, UUID> {



}

@org.springframework.stereotype.Repository
@AllArgsConstructor
class SourceRepositoryImpl implements SourceRepository {

    private final SqlSourceRepository repository;
    @Override public Source save( Source source) {
        System.err.println( "Source save: "+ source.getUpdate());
        return repository.save( source);
    }

    @Override
    public Optional<Source> findById( UUID id) {
        return repository.findById( id);
    }

}
