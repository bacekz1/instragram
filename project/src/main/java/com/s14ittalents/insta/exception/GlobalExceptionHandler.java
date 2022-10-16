package com.s14ittalents.insta.exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.function.Supplier;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @Autowired
    ExceptionDTO exceptionDTO;

    @ExceptionHandler(value = {DataNotFoundException.class})
    protected ResponseEntity<Object> handleConflict(RuntimeException ex, WebRequest request) {
        exceptionDTO.setTime(LocalDateTime.now());
        exceptionDTO.setStatus(HttpStatus.NOT_FOUND.value());
        exceptionDTO.setMessage(ex.getMessage());
        return handleExceptionInternal(ex, exceptionDTO,
                new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }
}
