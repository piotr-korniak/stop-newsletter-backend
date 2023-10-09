package com.stopnewsletter.backend.source;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@Accessors( chain= true)

@Table( name= "SOURCES")
@Entity
@Inheritance( strategy= InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn( name= "TYPE", discriminatorType= DiscriminatorType.STRING, length= 2)
public class Source {

    @Id
    //@UuidGenerator
    private UUID id;

    @Column( insertable= false, updatable= false)
    @Enumerated( EnumType.STRING)
    private SourceType type;    // FIXME: perhaps remove

    private String url;
    private String name;

    @Temporal( TemporalType.TIMESTAMP)
    private Date update;

    private boolean active;

}
