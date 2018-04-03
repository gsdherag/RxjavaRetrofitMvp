package com.shouxiu.rxjavaretrofit.base;

/**
 * @创建者 yeping
 * @创建时间 2017/9/5 11:08
 * @描述 ${TODO}
 */

public class BasePresenter<v extends BaseView> {
    private v view;

    public v getView(){
        return view;
    }

    public void attachView(v view){
        this.view = view;
    }

    public void detachView(){
        this.view = null;
    }
}
