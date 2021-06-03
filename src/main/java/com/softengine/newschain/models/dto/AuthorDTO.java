package com.softengine.newschain.models.dto;

import com.softengine.newschain.models.types.AuthorStatus;
import com.softengine.newschain.models.types.AuthorTypes;
import lombok.Data;

@Data
public class AuthorDTO {
    private Integer id;
    private String name;
    private String secretKey;
    private String surname;
    private String bolum;
    private AuthorTypes authorTypes;
    private AuthorStatus authorStatus;
}
