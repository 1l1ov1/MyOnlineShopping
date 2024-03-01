package com.wan.exception;

public class AccountExistException extends BaseException{
    public AccountExistException() {
        super();
    }

    public AccountExistException(String message) {
        super(message);
    }
}
