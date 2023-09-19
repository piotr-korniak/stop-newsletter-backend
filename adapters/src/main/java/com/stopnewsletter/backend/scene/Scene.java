package com.stopnewsletter.backend.scene;

import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

@Entity
@Table( name= "SCENES")
@Getter
public class Scene {

    @Id
    @GeneratedValue( generator= "UUID")
    @GenericGenerator( name= "UUID", strategy= "org.hibernate.id.UUIDGenerator")
    @Column( name= "ID")
    private UUID id;

    private String code;
    private String name;

    public Scene setCode( String code) {
        this.code= code;
        return this;
    }
    public Scene setName( String name) {
        this.name= name;
        return this;
    }

}
