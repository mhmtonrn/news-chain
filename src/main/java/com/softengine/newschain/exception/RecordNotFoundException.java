package com.softengine.newschain.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RecordNotFoundException extends Exception {

    private static final long serialVersionUID = 5041281841079302526L;

    private long errorCode;

    public RecordNotFoundException(String mesage) {
        super(mesage);
        this.errorCode = Long.parseLong("0001");
    }

    public RecordNotFoundException(Error error){
        super(error.getMessage());
        this.errorCode = error.getCode();
    }

    public RecordNotFoundException(String mesage, int errorCode) {
        super(mesage);
        if (errorCode < 0)
            errorCode *= -1;
        this.errorCode = Long.parseLong("" + errorCode);
    }
}