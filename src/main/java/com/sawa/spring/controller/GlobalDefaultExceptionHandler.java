package com.sawa.spring.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;

/**
 * User: omar
 */
@ControllerAdvice
public class GlobalDefaultExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalDefaultExceptionHandler.class);


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(Exception.class)
    @ResponseBody
    ErrorInfo handleBadRequest(HttpServletRequest req, Exception ex) {
        logger.error(ex.getMessage(), ex);
        return new ErrorInfo(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.value(), ex.getMessage(), req.getRequestURL().toString());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(UnknownResourceException.class)
    @ResponseBody
    ErrorInfo handleNotFound(HttpServletRequest req, Exception ex) {
        logger.error(ex.getMessage(), ex);
        return new ErrorInfo(HttpStatus.NOT_FOUND, HttpStatus.NOT_FOUND.value(), ex.getMessage(), req.getRequestURL().toString());
    }
}
