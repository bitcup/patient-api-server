package com.sawa.spring.controller;

/**
 * User: omar
 */
public class UnknownResourceException extends RuntimeException {

    public UnknownResourceException(String msg) {
        super(msg);
    }
}
