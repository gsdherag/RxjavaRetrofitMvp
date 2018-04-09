package com.shouxiu.rxjavaretrofit.view.home.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.shouxiu.rxjavaretrofit.R;
import com.shouxiu.rxjavaretrofit.api.bean.HomeCateList;
import com.shouxiu.rxjavaretrofit.api.bean.HomeRecommendHotCate;
import com.shouxiu.rxjavaretrofit.base.BaseFragment;
import com.shouxiu.rxjavaretrofit.mvp.home.p.HomeCatePresenter;
import com.shouxiu.rxjavaretrofit.mvp.home.v.HomeCateView;
import com.shouxiu.rxjavaretrofit.view.home.adapter.HomeOtherAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.Unbinder;

/**
 * @author yeping
 * @date 2018/4/3 9:31
 * TODO
 */

public class OtherHomeFragment extends BaseFragment<HomeCateView, HomeCatePresenter> implements HomeCateView {


    private static List<OtherHomeFragment> mOtherHomeFragments = new ArrayList<>();
    @BindView(R.id.other_content_recyclerView)
    RecyclerView otherContentRecyclerView;
    Unbinder unbinder;
    private HomeCateList mHomeCate;
    private HomeOtherAdapter adapter;

    @Override
    protected void lazyFetchData() {
        Bundle arguments = getArguments();
        mHomeCate = (HomeCateList) arguments.getSerializable("homeCateList");
        String show_order = arguments.getString("type");
        if (show_order.equals(mHomeCate.getShow_order())) {
            getPresenter().getHomeCate(getContext(), mHomeCate.getIdentification());
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home_two;
    }

    @Override
    protected void initView(View view) {

    }

    @Override
    protected HomeCateView createView() {
        return this;
    }

    @Override
    protected HomeCatePresenter createPresenter() {
        return new HomeCatePresenter();
    }


    public static OtherHomeFragment getInstance(HomeCateList args, int position) {
        OtherHomeFragment mInstance = new OtherHomeFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("homeCateList", args);
        bundle.putString("type", args.getShow_order());
        bundle.putInt("position", position - 1);
        mInstance.setArguments(bundle);
        mOtherHomeFragments.add(position - 1, mInstance);
        return mInstance;
    }

    @Override
    public void showErrorWithStatus(String msg) {

    }

    final RecyclerView.RecycledViewPool pool = new RecyclerView.RecycledViewPool() {
        @Override
        public void putRecycledView(RecyclerView.ViewHolder scrap) {
            super.putRecycledView(scrap);
        }

        @Override
        public RecyclerView.ViewHolder getRecycledView(int viewType) {
            final RecyclerView.ViewHolder recycledView = super.getRecycledView(viewType);
            return recycledView;
        }
    };

    @Override
    public void getOtherList(List<HomeRecommendHotCate> homeCates) {
        List<HomeRecommendHotCate> homeRecommendHotCates = new ArrayList<HomeRecommendHotCate>();
        homeRecommendHotCates.addAll(homeCates);
        for (int i = 0; i < homeRecommendHotCates.size(); i++) {
            if (homeRecommendHotCates.get(i).getRoom_list().size() < 4) {
                homeRecommendHotCates.remove(i);
            }
        }
        /**
         *  栏目 列表
         */
        adapter = new HomeOtherAdapter(getContext(), homeRecommendHotCates);
        otherContentRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        pool.setMaxRecycledViews(adapter.getItemViewType(0), 500);
        otherContentRecyclerView.setRecycledViewPool(pool);
        otherContentRecyclerView.setAdapter(adapter);
    }

    @Override
    public void getOtherListRefresh(List<HomeRecommendHotCate> homeCates) {

    }
}
