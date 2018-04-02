package com.shouxiu.rxjavaretrofit.utils;

import java.io.Closeable;
import java.io.IOException;

/**
 * @author yeping
 * @date 2018/3/31 17:34
 * 关闭Closeable对象工具方法
 */

public class CloseUtils {
    private CloseUtils(){

    }
    /**
     * 关闭Closeable对象
     */
    public static void closeCloseable(Closeable closeable){
        if(null != closeable){
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
