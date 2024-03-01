package com.wan.exception;

/**
 * 账号不存在异常
 */
public class AccountNotFountException extends BaseException{
    public AccountNotFountException() {
    }

    public AccountNotFountException(String message) {
        super(message);
    }
}
