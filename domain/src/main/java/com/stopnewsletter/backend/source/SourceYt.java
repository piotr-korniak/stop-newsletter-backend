package com.stopnewsletter.backend.source;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue( value= "YT")
public class SourceYt extends Source {
}
