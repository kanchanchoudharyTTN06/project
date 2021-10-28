package com.ttn.bootcamp.exceptions;

public class CustomExceptionResponse {
    private Object message;
    private int statusCode;
    private String status = "error";

    public Object getMessage() {
        return message;
    }

    public CustomExceptionResponse setMessage(Object message) {
        this.message = message;
        return this;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public CustomExceptionResponse setStatusCode(int statusCode) {
        this.statusCode = statusCode;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public CustomExceptionResponse setStatus(String status) {
        this.status = status;
        return this;
    }
}
