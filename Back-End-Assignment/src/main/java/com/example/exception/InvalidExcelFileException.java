package com.example.exception;

public class InvalidExcelFileException extends RuntimeException{
    public InvalidExcelFileException(String msg) {
        super(msg);
    }
}