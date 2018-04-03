package com.shouxiu.rxjavaretrofit;

import android.app.Application;
import android.content.Context;

/**
 * @author yeping
 * @date 2018/4/3 10:53
 * Application
 */

public class MyApp extends Application {

    private MyApp instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public MyApp getInstance() {
        return instance;
    }

    public Context getContext() {
        return getInstance().getApplicationContext();
    }
}
