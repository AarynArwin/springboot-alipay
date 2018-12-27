package com.geekerit.springbootalipay.utils;


/**
 * 自定义异常
 */
public class DisplayableException extends RuntimeException {
    public DisplayableException(String message) {
        super(message);
    }
}
