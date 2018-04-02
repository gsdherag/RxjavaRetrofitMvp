package com.shouxiu.rxjavaretrofit.net.transformer;

import com.shouxiu.rxjavaretrofit.net.exception.ExceptionHandle;
import com.shouxiu.rxjavaretrofit.net.exception.ServerException;
import com.shouxiu.rxjavaretrofit.net.response.HttpResponse;
import com.shouxiu.rxjavaretrofit.utils.LogUtil;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.functions.Function;

/**
 * @author yeping
 * @date 2018/3/31 14:16
 * 预处理响应
 */

public class ErrorTransformer<T> implements ObservableTransformer<HttpResponse<T>,T> {
    @Override
    public ObservableSource<T> apply(Observable<HttpResponse<T>> upstream) {
        return upstream.map(new Function<HttpResponse<T>, T>() {
            @Override
            public T apply(HttpResponse<T> tHttpResponse) throws Exception {
                if (tHttpResponse.getError()!=0) {
                    LogUtil.e("cc",tHttpResponse.toString());
                    //如果服务器端有错误信息返回，那么抛出异常，让下面的方法去捕获异常做统一处理
                    throw  new ServerException(String.valueOf(tHttpResponse.getData()),tHttpResponse.getError());
                }
                //                //服务器请求数据成功，返回里面的数据实体
                return tHttpResponse.getData();
            }
        }).onErrorResumeNext(new Function<Throwable, ObservableSource<? extends T>>() {
            @Override
            public ObservableSource<? extends T> apply(Throwable throwable) throws Exception {
                throwable.printStackTrace();
                return Observable.error(ExceptionHandle.handleException(throwable));
            }
        });
    }

    private static ErrorTransformer instance = null;

    private ErrorTransformer(){
    }
    /**
     * 双重校验锁单例(线程安全)
     */
    public static <T>ErrorTransformer<T> getInstance() {
        if (instance == null) {
            synchronized (ErrorTransformer.class) {
                if (instance == null) {
                    instance = new ErrorTransformer();
                }
            }
        }
        return instance;
    }
}
