package com.example.NGGG.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class WrongArgException extends RuntimeException{

    //private static final String MESSAGE = "메세지";
    public WrongArgException(String message) {
        super(message);
    }
}
