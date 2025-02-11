package com.youngpopeugene.exception;

public class PageException extends RuntimeException {
    public PageException(String message) {
        super(message);
    }
    public PageException() {
        super();
    }
}
