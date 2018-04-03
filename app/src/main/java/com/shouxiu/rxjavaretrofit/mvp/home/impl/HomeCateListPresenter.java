package com.shouxiu.rxjavaretrofit.mvp.home.impl;

import android.content.Context;
import android.widget.Toast;

import com.shouxiu.rxjavaretrofit.api.HomeCateList;
import com.shouxiu.rxjavaretrofit.base.BasePresenter;
import com.shouxiu.rxjavaretrofit.mvp.home.contract.HomeCateListView;
import com.shouxiu.rxjavaretrofit.mvp.home.model.HomeCateListModel;
import com.shouxiu.rxjavaretrofit.net.cache.XCCacheManager;
import com.shouxiu.rxjavaretrofit.net.callback.RxSubscriber;
import com.shouxiu.rxjavaretrofit.net.exception.ResponseThrowable;
import com.shouxiu.rxjavaretrofit.utils.GsonUtil;
import com.shouxiu.rxjavaretrofit.utils.NetworkUtil;

import java.util.List;

import static com.shouxiu.rxjavaretrofit.api.NetWorkApi.getHomeCateList;

/**
 * @author yeping
 * @date 2018/4/3 16:25
 * TODO
 */

public class HomeCateListPresenter extends BasePresenter<HomeCateListView> {

   private HomeCateListModel mHomeCateListModel;

    public HomeCateListPresenter() {
        mHomeCateListModel = new HomeCateListModel();
    }

    public void getHomeCateList(Context context) {
        mHomeCateListModel.getHomeCateList(context).subscribe(
                new RxSubscriber<List<HomeCateList>>() {     //订阅者响应类

                    @Override
                    protected String prepareCache() {
                        if (!NetworkUtil.isNetworkAvailable(context)) {
                            return XCCacheManager.getInstance(context).readCache(getHomeCateList);
                        }
                        return null;
                    }

                    @Override
                    public void onSuccess(List<HomeCateList> homeCateLists) {
                        String text = GsonUtil.object2Json(homeCateLists);
                        XCCacheManager.getInstance(context).writeCache(getHomeCateList, text);
                        getView().getHomeAllList(homeCateLists);
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
