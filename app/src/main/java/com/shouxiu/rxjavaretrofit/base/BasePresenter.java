package com.shouxiu.rxjavaretrofit.base;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

/**
 * @创建者 yeping
 * @创建时间 2017/9/5 11:08
 * @描述 p层基类
 */

public class BasePresenter<v extends BaseView> {
    //View 接口类型的弱引用
    private Reference<v> mViewRef;

    public v getView(){
        return mViewRef.get();
    }

    public void attachView(v view){
        this.mViewRef = new WeakReference<v>(view);
    }

    public void detachView(){
        if (mViewRef != null) {
            mViewRef.clear();
            mViewRef = null;
        }
    }

    public boolean isViewAttached() {
        return mViewRef != null && mViewRef.get() != null;
    }
}
