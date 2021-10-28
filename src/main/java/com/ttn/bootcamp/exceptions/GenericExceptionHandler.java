package com.ttn.bootcamp.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GenericExceptionHandler {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler(GenericException.class)
    public ResponseEntity<ExceptionResponse> handleBaseExceptions(GenericException exception) {

        ExceptionResponse exceptionResponse = new ExceptionResponse()
                .setMessage(exception.getMsg())
                .setStatusCode(exception.getStatusCode().value());
        logger.error("-->",exception);
        return new ResponseEntity<>(exceptionResponse, exception.getStatusCode());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handleException(Exception exception) {
        ExceptionResponse exceptionResponse = new ExceptionResponse().setMessage(exception.getMessage())
                .setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        logger.error("-->",exception);
        return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
