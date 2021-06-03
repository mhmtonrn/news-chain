package com.softengine.newschain.models.types;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum AuthorTypes {
    A("AUTHOR"),E("EDITOR"),AE("EDITOR_AUTHOR");

    @Getter
    private String type;

}
