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
    public ResponseEntity<CustomeExceptionResponse> handleBaseExceptions(GenericException exception) {

        CustomeExceptionResponse customeExceptionResponse = new CustomeExceptionResponse()
                .setMessage(exception.getMsg())
                .setStatusCode(exception.getStatusCode().value());
        logger.error("-->", exception);
        return new ResponseEntity<>(customeExceptionResponse, exception.getStatusCode());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<CustomeExceptionResponse> handleException(Exception exception) {
        CustomeExceptionResponse customeExceptionResponse = new CustomeExceptionResponse().setMessage(exception.getMessage())
                .setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        logger.error("-->", exception);
        return new ResponseEntity<>(customeExceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CustomeExceptionResponse> handleException(MethodArgumentNotValidException exception) {

        Map<String, String> errors = new HashMap<>();
        exception.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        CustomeExceptionResponse customeExceptionResponse = new CustomeExceptionResponse().setMessage(errors)
                .setStatusCode(HttpStatus.BAD_REQUEST.value());
        logger.error("-->", exception);
        return new ResponseEntity<>(customeExceptionResponse, HttpStatus.BAD_REQUEST);
    }

}
