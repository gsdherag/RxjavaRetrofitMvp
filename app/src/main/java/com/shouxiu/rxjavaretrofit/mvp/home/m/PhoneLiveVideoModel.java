package com.shouxiu.rxjavaretrofit.mvp.home.m;

import android.content.Context;

import com.shouxiu.rxjavaretrofit.api.HomeApi;
import com.shouxiu.rxjavaretrofit.api.NetWorkApi;
import com.shouxiu.rxjavaretrofit.api.bean.TempLiveVideoInfo;
import com.shouxiu.rxjavaretrofit.base.BaseModel;
import com.shouxiu.rxjavaretrofit.net.http.HttpUtils;
import com.shouxiu.rxjavaretrofit.net.transformer.DefaultTransformer;

import io.reactivex.Observable;

/**
 * @author yeping
 * @date 2018/4/11 10:27
 * TODO
 */

public class PhoneLiveVideoModel implements BaseModel {
    public Observable<TempLiveVideoInfo> getModelPhoneLiveVideoInfo(Context context, String roomId) {
        return HttpUtils.getInstance(context)
                .getRetrofitClient()
                .setBaseUrl(NetWorkApi.baseUrl1)
                .builder(HomeApi.class)
                .getModelPhoneLiveVideoInfo(roomId)
                //               进行预处理
                .compose(new DefaultTransformer<TempLiveVideoInfo>());
    }
}
