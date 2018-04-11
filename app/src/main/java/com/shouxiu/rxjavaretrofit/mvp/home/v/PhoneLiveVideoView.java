package com.shouxiu.rxjavaretrofit.mvp.home.v;

import com.shouxiu.rxjavaretrofit.api.bean.TempLiveVideoInfo;
import com.shouxiu.rxjavaretrofit.base.BaseView;

/**
 * @author yeping
 * @date 2018/4/11 10:12
 * TODO
 */

public interface PhoneLiveVideoView extends BaseView {
    void getViewPhoneLiveVideoInfo(TempLiveVideoInfo mLiveVideoInfo);
}
