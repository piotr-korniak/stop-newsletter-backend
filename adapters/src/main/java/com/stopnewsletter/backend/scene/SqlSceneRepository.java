package com.stopnewsletter.backend.scene;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SqlSceneRepository extends JpaRepository<Scene, UUID> {

    boolean existsByCode( String code);

}
