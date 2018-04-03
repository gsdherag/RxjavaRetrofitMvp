package com.shouxiu.rxjavaretrofit.fragment;

import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Toast;

import com.flyco.tablayout.SlidingTabLayout;
import com.shouxiu.rxjavaretrofit.R;
import com.shouxiu.rxjavaretrofit.api.HomeApi;
import com.shouxiu.rxjavaretrofit.api.HomeCateList;
import com.shouxiu.rxjavaretrofit.api.ParamsMapUtils;
import com.shouxiu.rxjavaretrofit.base.BaseFragment;
import com.shouxiu.rxjavaretrofit.base.BasePresenter;
import com.shouxiu.rxjavaretrofit.base.BaseView;
import com.shouxiu.rxjavaretrofit.net.cache.XCCacheManager;
import com.shouxiu.rxjavaretrofit.net.callback.RxSubscriber;
import com.shouxiu.rxjavaretrofit.net.exception.ResponseThrowable;
import com.shouxiu.rxjavaretrofit.net.http.HttpUtils;
import com.shouxiu.rxjavaretrofit.net.transformer.DefaultTransformer;
import com.shouxiu.rxjavaretrofit.utils.GsonUtil;
import com.shouxiu.rxjavaretrofit.utils.NetworkUtil;
import com.shouxiu.rxjavaretrofit.view.home.adapter.HomeAllListAdapter;

import java.util.List;

import butterknife.BindView;

import static com.shouxiu.rxjavaretrofit.api.NetWorkApi.getHomeCateList;

/**
 * @author yeping
 * @date 2018/4/3 9:31
 * TODO
 */

public class HomeFragment extends BaseFragment<BaseView, BasePresenter<BaseView>> implements BaseView {

    @BindView(R.id.sliding_tab)
    SlidingTabLayout slidingTab;
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    private HomeAllListAdapter mAdapter;
    private String[] mTitles;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home_one;
    }

    @Override
    protected void initView(View view) {

    }

    @Override
    protected BaseView createView() {
        return this;
    }

    @Override
    protected BasePresenter<BaseView> createPresenter() {
        return new BasePresenter();
    }

    @Override
    protected void lazyFetchData() {
        getHomeCateList();
    }


    private void getHomeCateList() {
        HttpUtils.getInstance(getContext())
                .getRetrofitClient()
                .builder(HomeApi.class)
                .getHomeCateList(ParamsMapUtils.getDefaultParams())
                .compose(new DefaultTransformer<List<HomeCateList>>())  //预处理错误信息
                .subscribe(new RxSubscriber<List<HomeCateList>>() {     //订阅者响应类

                    @Override
                    protected String prepareCache() {
                        if (!NetworkUtil.isNetworkAvailable(getContext())) {
                            return XCCacheManager.getInstance(getContext()).readCache(getHomeCateList);
                        }
                        return null;
                    }

                    @Override
                    public void onSuccess(List<HomeCateList> homeCateLists) {
                        String text = GsonUtil.object2Json(homeCateLists);
                        XCCacheManager.getInstance(getContext()).writeCache(getHomeCateList, text);
                        mTitles = new String[homeCateLists.size() + 1];
                        mTitles[0] = "推荐";
                        for (int i = 0; i < homeCateLists.size(); i++) {
                            mTitles[i + 1] = homeCateLists.get(i).getTitle();
                        }
                        viewpager.setOffscreenPageLimit(homeCateLists.size());
                        mAdapter = new HomeAllListAdapter(getChildFragmentManager(), homeCateLists, mTitles);
                        viewpager.setAdapter(mAdapter);
                        slidingTab.setViewPager(viewpager, mTitles);
                        showContentView();
                    }

                    @Override
                    public void onFail(ResponseThrowable t) {
                        Toast.makeText(getContext(), t.getErrorMsg(), Toast.LENGTH_SHORT).show();
                        showError();
                    }
                });
    }
}
