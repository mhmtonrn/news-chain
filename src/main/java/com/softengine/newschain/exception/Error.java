package com.softengine.newschain.exception;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Error {

    public static final Error RECORD_NOT_FOUND_ERROR = new Error(1,"Kayıt Bulunamadı");

    private long code;
    private String message;

}