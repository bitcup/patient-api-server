package com.sawa.spring.controller;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

/**
 * User: omar
 */
@EqualsAndHashCode
@ToString
public class ErrorInfo {

    @Getter
    @Setter
    private HttpStatus status;

    @Getter
    @Setter
    private int code;

    @Getter
    @Setter
    private String message;

    @Getter
    @Setter
    private String url;

    public ErrorInfo(HttpStatus status, int code, String message, String url) {
        this.status = status;
        this.code = code;
        this.message = message;
        this.url = url;
    }
}