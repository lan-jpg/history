package com.example.exception;

/*
全局异常捕获器
 */

import com.example.common.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice("com.example.controller")
public class GobalexceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GobalexceptionHandler.class);

    @ExceptionHandler(Exception.class)
    @ResponseBody//将result对象转换成json格式
    public Result error(Exception e){
        log.error("系统错误"+e);
        return Result.error("系统异常");
    }

    @ExceptionHandler(CustomerException.class)
    @ResponseBody//将result对象转换成json格式
    public Result CustomerError(CustomerException e){
        log.error("自定义错误"+e.getMsg());
        return Result.error(e.getCode(), e.getMsg());
    }
}
