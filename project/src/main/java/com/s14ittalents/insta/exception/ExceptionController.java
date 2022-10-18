package com.s14ittalents.insta.exception;

import lombok.SneakyThrows;
import org.apache.tomcat.util.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.LocalDateTime;
import java.util.ArrayList;

@ControllerAdvice
public abstract class ExceptionController extends ResponseEntityExceptionHandler {
    @Autowired
    ExceptionDTO exceptionDTO;


    Logger logger = LoggerFactory.getLogger(ExceptionController.class);


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
        StringWriter errors = new StringWriter();
        ex.printStackTrace(new PrintWriter(errors));
        logger.error(errors.toString());

        String errorMessage = new ArrayList<>(ex.getConstraintViolations()).get(0).getMessage();
        exceptionDTO.setTime(LocalDateTime.now());
        exceptionDTO.setStatus(HttpStatus.EXPECTATION_FAILED.value());
        exceptionDTO.setMessage(errorMessage);
        return handleExceptionInternal(ex, exceptionDTO,
                new HttpHeaders(), HttpStatus.EXPECTATION_FAILED, request);
    }

    private ResponseEntity<Object> buildErr(Exception ex, HttpStatus status, WebRequest request) {
        StringWriter errors = new StringWriter();
        ex.printStackTrace(new PrintWriter(errors));
        logger.error(errors.toString());

        ex.printStackTrace();
        exceptionDTO.setTime(LocalDateTime.now());
        exceptionDTO.setStatus(status.value());
        exceptionDTO.setMessage(ex.getMessage());
        return handleExceptionInternal(ex, exceptionDTO,
                new HttpHeaders(), status, request);
    }
}
