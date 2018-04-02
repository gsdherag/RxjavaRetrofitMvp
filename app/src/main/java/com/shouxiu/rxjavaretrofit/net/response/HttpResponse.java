package com.shouxiu.rxjavaretrofit.net.response;

/**
 * @author yeping
 * @date 2018/3/31 14:09
 * 约定服务器公共的json数据
 */

public class HttpResponse<T> {

    private int error ;

    private T data;


    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
    @Override
    public String toString() {
        return "{" +
                "error:'" + error + '\'' +
                ", data:" + data +
                '}';
    }
}
