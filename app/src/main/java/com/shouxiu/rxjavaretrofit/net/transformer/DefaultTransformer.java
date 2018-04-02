package com.shouxiu.rxjavaretrofit.net.transformer;

import com.shouxiu.rxjavaretrofit.net.response.HttpResponse;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author yeping
 * @date 2018/3/31 14:14
 * 默认预处理结果
 */

public class DefaultTransformer<T> implements ObservableTransformer<HttpResponse<T>,T>{


    @Override
    public ObservableSource<T> apply(Observable<HttpResponse<T>> upstream) {
        return upstream.subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .compose(ErrorTransformer.<T>getInstance())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
