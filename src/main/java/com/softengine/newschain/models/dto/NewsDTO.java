package com.softengine.newschain.models.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class NewsDTO {
    private String title;
    private String content;


}
