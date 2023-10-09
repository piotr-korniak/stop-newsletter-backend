package com.stopnewsletter.backend.catalog.source;

import com.stopnewsletter.backend.catalog.source.dto.SourceDto;
import com.stopnewsletter.backend.source.SourceType;

import java.util.List;

public interface SourceQueryRepository {

    public List<SourceDto> findTypeByActive(SourceType type);

    List<SourceDto> findAllByTypeAndActive( SourceType sourceType, Boolean aTrue);
}
