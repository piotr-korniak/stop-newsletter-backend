package com.stopnewsletter.backend.blog;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.SecondaryTable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
@SecondaryTable( name = "CONTENTS")
public class PostId {

    private int id;

    @Column( table="CONTETNS", name= "SOURCE_ID", insertable=false, updatable=false)
    private UUID blogId;


}
