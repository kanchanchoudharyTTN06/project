package com.ttn.bootcamp.exceptions;

import java.util.Map;
import java.util.Objects;

public class CustomeExceptionResponse {
    private Object message;
    private int statusCode;
    private String status = "error";
    private Map<String, String> messages;

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

    public Map<String, String> getMessages() {
        return messages;
    }

    public CustomeExceptionResponse setMessages(Map<String, String> messages) {
        this.messages = messages;
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
