package com.softengine.newschain.models.dto;

import com.softengine.newschain.models.types.NewsStatus;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
public class NewsDTO {

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
