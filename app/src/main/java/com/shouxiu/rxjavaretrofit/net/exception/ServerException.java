package com.shouxiu.rxjavaretrofit.net.exception;

/**
 * @author yeping
 * @date 2018/3/31 14:19
 * 服务异常处理基类
 */

public class ServerException extends RuntimeException {
    public int code;
    public String message;

    public ServerException(String message, int code) {
        this.message=message;
        this.code = code;
    }
}