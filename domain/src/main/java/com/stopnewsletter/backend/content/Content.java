package com.stopnewsletter.backend.content;

import com.stopnewsletter.backend.source.Source;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.UuidGenerator;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Table( name= "CONTENTS")
@Entity
@Inheritance( strategy= InheritanceType.JOINED)
@DiscriminatorColumn( name= "TYPE", discriminatorType= DiscriminatorType.STRING, length= 2)
public class Content {

    @Getter
    @Id
    @UuidGenerator
    private UUID id;
    @ManyToOne
    private Source source;
    @Column( name= "SOURCE_ID", insertable=false, updatable=false)
    private UUID sourceId;

    @Column( name= "NAME")
    private String title;

    @Temporal( TemporalType.TIMESTAMP)
    private Date date;

    @ManyToMany( cascade= {CascadeType.MERGE}, fetch= FetchType.EAGER)
    @JoinTable( name= "CONTENT_CREATORS",
            joinColumns= @JoinColumn( name= "CONTENT_ID", referencedColumnName= "ID"),
            inverseJoinColumns={ @JoinColumn( name= "CREATOR_ID", referencedColumnName= "ID")})
    private Set<Creator> creators= new HashSet<>();

    public Content setSource( Source source) {
        this.source= source;
        return this;
    }

    public Content setSource( UUID sourceId) {
        this.sourceId = sourceId;
        return this;
    }

    @Column( length= 192)
    public Content setTitle( String title) {
        this.title= title.substring( 0, Math.min( title.length(), 192));;
        return this;
    }

    public Content setDate( Date date){
        this.date= date;
        return this;
    }
    public Content setCreators( Set<Creator> creators) {
        this.creators= creators;
        return this;
    }
}
