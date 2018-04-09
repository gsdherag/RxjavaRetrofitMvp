package com.shouxiu.rxjavaretrofit.mvp.home.m;

import android.content.Context;

import com.shouxiu.rxjavaretrofit.api.HomeApi;
import com.shouxiu.rxjavaretrofit.api.ParamsMapUtils;
import com.shouxiu.rxjavaretrofit.api.bean.HomeRecommendHotCate;
import com.shouxiu.rxjavaretrofit.base.BaseModel;
import com.shouxiu.rxjavaretrofit.net.http.HttpUtils;
import com.shouxiu.rxjavaretrofit.net.transformer.DefaultTransformer;

import java.util.List;

import io.reactivex.Observable;

/**
 * @author yeping
 * @date 2018/4/3 16:19
 * TODO
 */

public class HomeCateModel implements BaseModel {

    public Observable<List<HomeRecommendHotCate>> getHomeCate(Context context, String identification) {
        return HttpUtils.getInstance(context)
                .getRetrofitClient()
                .builder(HomeApi.class)
                .getHomeCate(ParamsMapUtils.getHomeCate(identification))
                //               进行预处理
                .compose(new DefaultTransformer<List<HomeRecommendHotCate>>());
    }
}
