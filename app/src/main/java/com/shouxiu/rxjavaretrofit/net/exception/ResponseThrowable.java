package com.shouxiu.rxjavaretrofit.net.exception;

/**
 * @author yeping
 * @date 2018/3/31 14:21
 * 响应异常处理基类
 */

public class ResponseThrowable extends Exception {
    public int code;
    public String message;

    public ResponseThrowable(Throwable throwable, int code) {
        super(throwable);
        this.code = code;
    }

    public String getErrorMsg(){
        return message;
    }
}
