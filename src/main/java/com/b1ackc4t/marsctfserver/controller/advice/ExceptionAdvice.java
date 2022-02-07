package com.b1ackc4t.marsctfserver.controller.advice;

import com.b1ackc4t.marsctfserver.pojo.ReturnRes;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler(Exception.class)
    public ReturnRes doException(Exception e) {
        e.printStackTrace();
        return new ReturnRes(false, "服务器故障 请稍后再试");
    }
}
