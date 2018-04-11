package com.shouxiu.rxjavaretrofit.mvp.home.p;

import android.content.Context;
import android.widget.Toast;

import com.shouxiu.rxjavaretrofit.api.bean.TempLiveVideoInfo;
import com.shouxiu.rxjavaretrofit.base.BasePresenter;
import com.shouxiu.rxjavaretrofit.mvp.home.m.PhoneLiveVideoModel;
import com.shouxiu.rxjavaretrofit.mvp.home.v.PhoneLiveVideoView;
import com.shouxiu.rxjavaretrofit.net.cache.XCCacheManager;
import com.shouxiu.rxjavaretrofit.net.callback.RxSubscriber;
import com.shouxiu.rxjavaretrofit.net.exception.ResponseThrowable;
import com.shouxiu.rxjavaretrofit.utils.GsonUtil;
import com.shouxiu.rxjavaretrofit.utils.NetworkUtil;

import static com.shouxiu.rxjavaretrofit.api.NetWorkApi.getHomeCate;
import static com.shouxiu.rxjavaretrofit.api.NetWorkApi.getHomePhoneLiveVideo;

/**
 * @author yeping
 * @date 2018/4/11 11:04
 * TODO
 */

public class PhoneLiveVideoPresenter extends BasePresenter<PhoneLiveVideoView> {
    private PhoneLiveVideoModel mPhoneLiveVideoModel;

    public PhoneLiveVideoPresenter() {
        mPhoneLiveVideoModel = new PhoneLiveVideoModel();
    }

    public void getModelPhoneLiveVideoInfo(Context context, String roomId) {
        mPhoneLiveVideoModel.getModelPhoneLiveVideoInfo(context, roomId).subscribe(
                new RxSubscriber<TempLiveVideoInfo>() {     //订阅者响应类

                    @Override
                    protected String prepareCache() {
                        if (!NetworkUtil.isNetworkAvailable(context)) {
                            return XCCacheManager.getInstance(context).readCache(getHomePhoneLiveVideo);
                        }
                        return null;
                    }

                    @Override
                    public void onSuccess(TempLiveVideoInfo homeCateLists) {
                        String text = GsonUtil.object2Json(homeCateLists);
                        XCCacheManager.getInstance(context).writeCache(getHomeCate, text);
                        getView().getViewPhoneLiveVideoInfo(homeCateLists);
                    }

                    @Override
                    public void onFail(ResponseThrowable t) {
                        Toast.makeText(context, t.getErrorMsg(), Toast.LENGTH_SHORT).show();
                        getView().showErrorWithStatus(t.getErrorMsg());
                    }
                }
        );
    }
}
