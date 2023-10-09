package com.stopnewsletter.backend.scene;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

@Entity
@Table( name= "SCENES")
@Getter
@Setter
@Accessors( chain= true)
public class Scene {

    @Id
    @GeneratedValue( generator= "UUID")
    @GenericGenerator( name= "UUID", strategy= "org.hibernate.id.UUIDGenerator")
    @Column( name= "ID")
    private UUID id;

    private String code;
    private String name;
    private UUID notion;

}
