package com.stopnewsletter.backend.blog;

import com.stopnewsletter.backend.source.Source;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue( value= "WP")
public class Blog extends Source {
}
