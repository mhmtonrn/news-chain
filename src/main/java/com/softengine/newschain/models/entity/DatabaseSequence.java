package com.softengine.newschain.models.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "database_sequences")
@Data
public class DatabaseSequence {

    @Id
    private String id;

    private Integer seq;
}