package com.example.NGGG.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class UnAuthorizedException extends RuntimeException{
    public UnAuthorizedException(){
        super();
    }
    public UnAuthorizedException(String msg){
        super(msg);
    }
}