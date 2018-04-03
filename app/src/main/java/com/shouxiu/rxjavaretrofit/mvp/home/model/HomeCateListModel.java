package com.shouxiu.rxjavaretrofit.mvp.home.model;

import android.content.Context;

import com.shouxiu.rxjavaretrofit.api.HomeApi;
import com.shouxiu.rxjavaretrofit.api.HomeCateList;
import com.shouxiu.rxjavaretrofit.api.ParamsMapUtils;
import com.shouxiu.rxjavaretrofit.mvp.home.contract.HomeCateListContract;
import com.shouxiu.rxjavaretrofit.net.http.HttpUtils;
import com.shouxiu.rxjavaretrofit.net.transformer.DefaultTransformer;

import java.util.List;

import io.reactivex.Observable;

/**
 * @author yeping
 * @date 2018/4/3 16:19
 * TODO
 */

public class HomeCateListModel implements HomeCateListContract.Model {
    @Override
    public Observable getHomeCateList(Context context) {
        return  HttpUtils.getInstance(context)
                .getRetrofitClient()
                .builder(HomeApi.class)
                .getHomeCateList(ParamsMapUtils.getDefaultParams())
                .compose(new DefaultTransformer<List<HomeCateList>>());  //预处理错误信息;
    }
}
