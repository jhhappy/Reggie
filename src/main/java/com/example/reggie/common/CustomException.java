package com.example.reggie.common;

/**
 * 自定义业务异常
 * @author Ustinain
 * @Time 2023/5/9  17:42
 */
public class CustomException extends RuntimeException{
    public CustomException(String message){
        super(message);
    }

}
