package com.ttn.bootcamp.exceptions;

import org.springframework.http.HttpStatus;

public class GenericException extends Exception {

    String msg;
    HttpStatus statusCode;

    public GenericException(String msg, HttpStatus statusCode) {
        super(msg);
        this.msg = msg;
        this.statusCode = statusCode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public HttpStatus getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(HttpStatus statusCode) {
        this.statusCode = statusCode;
    }
}
