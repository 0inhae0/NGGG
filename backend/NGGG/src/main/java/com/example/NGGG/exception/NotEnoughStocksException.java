package com.example.NGGG.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class NotEnoughStocksException extends RuntimeException{
    public NotEnoughStocksException(String message) {
        super(message);
    }
}
