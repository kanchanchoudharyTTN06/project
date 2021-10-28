package com.ttn.bootcamp.exceptions;

public class CustomeExceptionResponse {
    private Object message;
    private int statusCode;
    private String status = "error";

    public Object getMessage() {
        return message;
    }

    public CustomeExceptionResponse setMessage(Object message) {
        this.message = message;
        return this;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public CustomeExceptionResponse setStatusCode(int statusCode) {
        this.statusCode = statusCode;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public CustomeExceptionResponse setStatus(String status) {
        this.status = status;
        return this;
    }
}
