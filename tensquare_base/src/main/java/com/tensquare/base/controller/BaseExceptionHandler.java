package com.tensquare.base.controller;


import entity.Result;
import entity.StatusCode;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class BaseExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public Result  execption(Exception e) {
        e.printStackTrace();
        return  new Result(false, StatusCode.ERROR,e.getMessage());
    }
}
