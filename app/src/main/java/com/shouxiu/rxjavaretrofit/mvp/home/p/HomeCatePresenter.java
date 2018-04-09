package com.shouxiu.rxjavaretrofit.mvp.home.p;

import android.content.Context;
import android.widget.Toast;

import com.shouxiu.rxjavaretrofit.api.bean.HomeRecommendHotCate;
import com.shouxiu.rxjavaretrofit.base.BasePresenter;
import com.shouxiu.rxjavaretrofit.mvp.home.m.HomeCateModel;
import com.shouxiu.rxjavaretrofit.mvp.home.v.HomeCateView;
import com.shouxiu.rxjavaretrofit.net.cache.XCCacheManager;
import com.shouxiu.rxjavaretrofit.net.callback.RxSubscriber;
import com.shouxiu.rxjavaretrofit.net.exception.ResponseThrowable;
import com.shouxiu.rxjavaretrofit.utils.GsonUtil;
import com.shouxiu.rxjavaretrofit.utils.NetworkUtil;

import java.util.List;

import static com.shouxiu.rxjavaretrofit.api.NetWorkApi.getHomeCate;

/**
 * @author yeping
 * @date 2018/4/3 16:25
 * TODO
 */

public class HomeCatePresenter extends BasePresenter<HomeCateView> {

    private HomeCateModel mHomeCateModel;

    public HomeCatePresenter() {
        mHomeCateModel = new HomeCateModel();
    }

    public void getHomeCate(Context context, String identification) {
        mHomeCateModel.getHomeCate(context, identification).subscribe(
                new RxSubscriber<List<HomeRecommendHotCate>>() {     //订阅者响应类

                    @Override
                    protected String prepareCache() {
                        if (!NetworkUtil.isNetworkAvailable(context)) {
                            return XCCacheManager.getInstance(context).readCache(getHomeCate);
                        }
                        return null;
                    }

                    @Override
                    public void onSuccess(List<HomeRecommendHotCate> homeCateLists) {
                        String text = GsonUtil.object2Json(homeCateLists);
                        XCCacheManager.getInstance(context).writeCache(getHomeCate, text);
                        getView().getOtherList(homeCateLists);
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
