package com.shouxiu.rxjavaretrofit.net.cache;

/**
 * @author yeping
 * @date 2018/3/31 17:23
 * TODO
 */

public interface Cache {
    String get(final String key);
    void put(final String key, final String value);
    boolean remove(final String key);
}
