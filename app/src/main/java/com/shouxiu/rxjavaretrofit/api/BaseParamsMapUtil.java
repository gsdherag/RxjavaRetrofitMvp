package com.shouxiu.rxjavaretrofit.api;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author yeping
 * @date 2018/3/31 14:13
 * 公共的参数集合
 */

public class BaseParamsMapUtil {
    /**
     * 公共的参数集合
     *
     * @return
     */
    public static Map<String, String> getParamsMap() {
        Map<String, String> paramsmap = new LinkedHashMap<>();
        paramsmap.put("client_sys", "android");
        paramsmap.put("aid", "android1");
        paramsmap.put("time",System.currentTimeMillis()+"");
        return paramsmap;
    }

}
