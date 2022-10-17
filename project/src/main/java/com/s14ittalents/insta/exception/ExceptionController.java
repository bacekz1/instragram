package com.s14ittalents.insta.exception;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;

@ControllerAdvice
public abstract class ExceptionController extends ResponseEntityExceptionHandler {
    @Autowired
    ExceptionDTO exceptionDTO;
    
    @ExceptionHandler(value = {DataNotFoundException.class})
    ResponseEntity<Object> handleDataNotFound(DataNotFoundException ex, WebRequest request) {
        return buildErr(ex, HttpStatus.NOT_FOUND, request);
    }
    @ExceptionHandler(value = {Exception.class})
    ResponseEntity<Object> handleAnyException(Exception ex, WebRequest request) {
        return buildErr(ex, HttpStatus.INTERNAL_SERVER_ERROR, request);
    }
    @ExceptionHandler(value = {NoAuthException.class})
    ResponseEntity<Object> handleNoAuthorisation(Exception ex, WebRequest request) {
        return buildErr(ex, HttpStatus.UNAUTHORIZED, request);
    }
    @ExceptionHandler(value = {ConstraintViolationException.class})
    protected ResponseEntity<Object> handleConstraintAnnotations(ConstraintViolationException ex, WebRequest request) {
        exceptionDTO.setTime(LocalDateTime.now());
        exceptionDTO.setStatus(HttpStatus.EXPECTATION_FAILED.value());
        exceptionDTO.setMessage(ex.getMessage());
        return handleExceptionInternal(ex, exceptionDTO,
                new HttpHeaders(), HttpStatus.EXPECTATION_FAILED, request);
    }

    private ResponseEntity<Object> buildErr(Exception ex, HttpStatus status, WebRequest request) {
        ex.printStackTrace();
        exceptionDTO.setTime(LocalDateTime.now());
        exceptionDTO.setStatus(status.value());
        exceptionDTO.setMessage(ex.getMessage());
        return handleExceptionInternal(ex, exceptionDTO,
                new HttpHeaders(), status, request);
    }
}
