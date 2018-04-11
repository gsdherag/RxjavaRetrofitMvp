package com.shouxiu.rxjavaretrofit.api;

/**
 * @author yeping
 * @date 2018/3/31 14:10
 * TODO
 */

public class NetWorkApi {
    //    Base地址
    public static String baseUrl = "http://capi.douyucdn.cn";
    public static String baseUrl1 = "https://m.douyu.com";
    //     首页列表
    public static final String getHomeCateList = "/api/homeCate/getCateList";
    //     列表详情
    public static final String getHomeCate = "/api/homeCate/getHotRoom";
    //     获取手机房间
    public static final String getHomePhoneLiveVideo = "/html5/live";
}
