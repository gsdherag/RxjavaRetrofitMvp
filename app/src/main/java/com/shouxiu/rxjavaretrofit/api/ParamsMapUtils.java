package com.shouxiu.rxjavaretrofit.api;

import java.util.Map;

/**
 * @author yeping
 * @date 2018/3/31 14:12
 * TODO
 */

public class ParamsMapUtils  extends BaseParamsMapUtil {

    private static Map<String, String> mapparam;

    /**
     * 默认参数
     *
     * @return
     */
    public static Map<String, String> getDefaultParams() {
        return getParamsMap();
    }

    /**
     * 首页 列表详情
     *
     * @param identification
     * @return
     */
    public static Map<String, String> getHomeCate(String identification) {
        mapparam = getDefaultParams();
        mapparam.put("identification", identification);
        return mapparam;
    }

    public static Map<String, String> getHomeCarousel() {
        mapparam = getDefaultParams();
        mapparam.put("version", "2.421");
        return mapparam;
    }
}