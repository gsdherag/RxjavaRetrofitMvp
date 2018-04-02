package com.shouxiu.rxjavaretrofit.net.callback;

import com.google.gson.reflect.TypeToken;
import com.shouxiu.rxjavaretrofit.net.exception.ResponseThrowable;
import com.shouxiu.rxjavaretrofit.utils.GsonUtil;
import com.shouxiu.rxjavaretrofit.utils.LogUtil;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * @author yeping
 * @date 2018/3/31 14:28
 * 观察者回调
 */

public abstract class RxSubscriber<T> implements Observer<T> {

    @Override
    public void onSubscribe(Disposable d) {
        onStart();
    }

    protected void onStart() {

        String json = prepareCache();
        if (json != null) {
            T homeCateLists = GsonUtil.json2Object(json, new TypeToken<T>() {
            });
            onNext(homeCateLists);
            onComplete();
        }
    }

    protected abstract String prepareCache();

    ;

    /**
     * 获取网络数据
     *
     * @param t
     */
    @Override
    public void onNext(T t) {

        onSuccess(t);
    }

    @Override
    public void onError(Throwable e) {
        LogUtil.e("错误信息:" + e.getMessage());
        if (e instanceof ResponseThrowable) {
            onFail((ResponseThrowable) e);
        } else {
            onFail(new ResponseThrowable(e, 1000));
        }
    }

    @Override
    public void onComplete() {

    }

    public abstract void onSuccess(T t);

    public abstract void onFail(ResponseThrowable t);
}
