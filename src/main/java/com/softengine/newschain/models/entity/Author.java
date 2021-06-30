package com.softengine.newschain.models.entity;

import com.softengine.newschain.models.types.AuthorStatus;
import com.softengine.newschain.models.types.AuthorTypes;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "author")
@Data
public class Author {

    @Transient
    public transient static final String SEQUENCE_NAME = "author";

    @Id
    @Indexed
    private Integer id;
    private String name;
    private String secretKey;
    private String surname;
    private String bolum;
    private AuthorTypes authorTypes;
    private AuthorStatus authorStatus;
}
