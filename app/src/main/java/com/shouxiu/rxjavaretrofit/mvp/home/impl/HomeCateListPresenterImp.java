package com.shouxiu.rxjavaretrofit.mvp.home.impl;

import android.content.Context;
import android.widget.Toast;

import com.shouxiu.rxjavaretrofit.api.HomeCateList;
import com.shouxiu.rxjavaretrofit.base.BaseView;
import com.shouxiu.rxjavaretrofit.mvp.home.contract.HomeCateListContract;
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

public class HomeCateListPresenterImp<V extends BaseView> extends HomeCateListContract.Presenter {

   private HomeCateListModel mHomeCateListModel;

    public HomeCateListPresenterImp(Context context) {
        mHomeCateListModel = new HomeCateListModel();
    }

    @Override
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
//                        mTitles = new String[homeCateLists.size() + 1];
//                        mTitles[0] = "推荐";
//                        for (int i = 0; i < homeCateLists.size(); i++) {
//                            mTitles[i + 1] = homeCateLists.get(i).getTitle();
//                        }
//                        viewpager.setOffscreenPageLimit(homeCateLists.size());
//                        mAdapter = new HomeAllListAdapter(getChildFragmentManager(), homeCateLists, mTitles);
//                        viewpager.setAdapter(mAdapter);
//                        slidingTab.setViewPager(viewpager, mTitles);
//                        showContentView();
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
