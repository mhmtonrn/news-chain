package com.softengine.newschain.models.dto;

import lombok.Data;

import java.io.InputStream;

@Data
public class Video {
    private String title;
    private InputStream stream;
}