package com.ttn.bootcamp.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalControllerAdvice {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler(GenericException.class)
    public ResponseEntity<CustomExceptionResponse> handleBaseExceptions(GenericException exception) {

        CustomExceptionResponse customExceptionResponse = new CustomExceptionResponse()
                .setMessage(exception.getMsg())
                .setStatusCode(exception.getStatusCode().value());
        logger.error("-->", exception);
        return new ResponseEntity<>(customExceptionResponse, exception.getStatusCode());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<CustomExceptionResponse> handleException(Exception exception) {
        CustomExceptionResponse customExceptionResponse = new CustomExceptionResponse().setMessage(exception.getMessage())
                .setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        logger.error("-->", exception);
        return new ResponseEntity<>(customExceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CustomExceptionResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {

        Map<String, String> errors = new HashMap<>();
        exception.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        CustomExceptionResponse customExceptionResponse = new CustomExceptionResponse().setMessage(errors)
                .setStatusCode(HttpStatus.BAD_REQUEST.value());
        logger.error("-->", exception);
        return new ResponseEntity<>(customExceptionResponse, HttpStatus.BAD_REQUEST);
    }

}
