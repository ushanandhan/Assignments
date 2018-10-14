package com.example.exception;

public class InvalidObjectFieldNameException extends RuntimeException{
    public InvalidObjectFieldNameException(String msg) {
        super(msg);
    }
}