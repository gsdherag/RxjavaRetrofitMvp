package com.shouxiu.rxjavaretrofit.api;

import com.shouxiu.rxjavaretrofit.net.response.HttpResponse;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

import static com.shouxiu.rxjavaretrofit.api.NetWorkApi.getHomeCateList;

/**
 * @author yeping
 * @date 2018/3/31 14:07
 * 首页api
 */

public interface HomeApi {

    /**
     * 首页分类列表
     *
     * @return
     */
    @GET(getHomeCateList)
    Observable<HttpResponse<List<HomeCateList>>> getHomeCateList(@QueryMap Map<String, String> params);
}