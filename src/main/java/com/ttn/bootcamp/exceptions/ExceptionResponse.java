package com.ttn.bootcamp.exceptions;

public class ExceptionResponse {
    String message;
    int statusCode;
    String status = "error";

    public String getMessage() {
        return message;
    }

    public ExceptionResponse setMessage(String message) {
        this.message = message;
        return this;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public ExceptionResponse setStatusCode(int statusCode) {
        this.statusCode = statusCode;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public ExceptionResponse setStatus(String status) {
        this.status = status;
        return this;
    }
}
