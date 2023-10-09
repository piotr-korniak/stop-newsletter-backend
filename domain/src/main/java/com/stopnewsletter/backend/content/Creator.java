package com.stopnewsletter.backend.content;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Table( name= "CREATORS")
@Entity
public class Creator {

    @Id
    @UuidGenerator
    private UUID id;

    private String name;

    public Creator name( String name) {
        this.name= name;
        return this;
    }
}
