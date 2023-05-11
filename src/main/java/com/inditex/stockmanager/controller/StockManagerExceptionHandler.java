package com.inditex.stockmanager.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class StockManagerExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<Object> handleCustomException(CustomException ex) {
        return Response.generateResponse(ex.getMessage(), HttpStatus.BAD_REQUEST, ex.getCause());
    }

    @ExceptionHandler(DBAccessException.class)
    public ResponseEntity<Object> handleDBAccessException(DBAccessException ex) {
        return Response.generateResponse(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, ex.getCause());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException(Exception ex) {
        return Response.generateResponse(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, ex.getCause());
    }
}
