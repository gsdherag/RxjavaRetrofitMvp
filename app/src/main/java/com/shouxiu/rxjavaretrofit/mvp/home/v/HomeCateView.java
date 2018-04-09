package com.shouxiu.rxjavaretrofit.mvp.home.v;

import com.shouxiu.rxjavaretrofit.api.bean.HomeRecommendHotCate;
import com.shouxiu.rxjavaretrofit.base.BaseView;

import java.util.List;

/**
 * @author yeping
 * @date 2018/4/3 16:12
 * TODO
 */

public interface HomeCateView extends BaseView {

    void getOtherList(List<HomeRecommendHotCate> homeCates);
    void getOtherListRefresh(List<HomeRecommendHotCate> homeCates);
}
