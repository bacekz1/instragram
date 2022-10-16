package com.s14ittalents.insta.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.function.Supplier;

public class DataNotFoundException extends RuntimeException {
   public DataNotFoundException(String message){
        super(message);
    }

}
