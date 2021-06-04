package com.softengine.newschain.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Document(collection = "photos")
public class Photo {

    public Photo(String title){
        this.title = title;
    }

    @Id
    private String id;

    private String title;
        
    private Binary image;
}