package com.softengine.newschain.models.entity;

import com.softengine.newschain.models.types.NewsStatus;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
@Document(collection = "news")
public class News {

    public static final String SEQUENCE_NAME = "news";
    @Id
    private Integer id;

    private String title;
    private String content;
    private String photoId;
    private String videoId;
    private Integer authorId;
    private Integer editorId;
    private NewsStatus newsStatus;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime publishDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime editDate;

}
