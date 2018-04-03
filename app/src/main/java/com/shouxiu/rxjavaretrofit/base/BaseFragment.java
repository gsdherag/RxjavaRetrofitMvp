package com.shouxiu.rxjavaretrofit.base;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.shouxiu.rxjavaretrofit.R;
import com.shouxiu.rxjavaretrofit.net.callback.PerfectClickListener;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @创建者 yeping
 * @创建时间 2017/9/5 14:04
 * @描述 ${TODO}
 */
public abstract class BaseFragment<V extends BaseView, P extends BasePresenter<V>> extends Fragment {
    private P presenter;
    private V view;
    protected ViewGroup rootView;
    private Unbinder unbinder;
    // 标识fragment视图已经初始化完毕
    private boolean isViewPrepared;
    // 标识已经触发过懒加载数据
    private boolean hasFetchData;
    //是否显示
    protected boolean mIsVisible;
    //加载布局
    private LinearLayout mLlProgressBar;
    //加载动画
    private AnimationDrawable mAnimationDawable;
    //加载失败布局
    private LinearLayout mLlErrorRefresh;
    // 内容布局
    protected RelativeLayout mContainer;

    public P getPresenter() {
        return presenter;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        System.out.println("onCreate");
        //防止Fragment从新创建会出现重叠
        if (savedInstanceState != null && getTag() != null) {
            FragmentManager manager = getFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            List<Fragment> list = manager.getFragments();
            if (null != list && list.size() > 0) {
                for (Fragment fragment : list) {
                    if (fragment != null && getTag().equals(fragment.getTag())) {
                        transaction.remove(fragment);
                        break;
                    }
                }
            }
            transaction.commit();
        }
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View ll = inflater.inflate(R.layout.fragment_base, null);
        mContainer = (RelativeLayout) ll.findViewById(R.id.rl_content_part);
        rootView = (ViewGroup) inflater.inflate(getLayoutId(), null, false);
        mContainer.addView(rootView);
        unbinder = ButterKnife.bind(this, rootView);
        return ll;
    }

    /**
     * 在这里实现Fragment数据的缓加载.
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            lazyFetchDataIfPrepared();
        }
    }

    /**
     * 进行懒加载
     */
    private void lazyFetchDataIfPrepared() {
        // 用户可见fragment && 没有加载过数据 && 视图已经准备完毕
        if (getUserVisibleHint() && !hasFetchData && isViewPrepared) {
            hasFetchData = true;
            if (presenter != null) {
                lazyFetchData();
            } else {
                hasFetchData = false;
            }
        }

    }

    /**
     * 懒加载的方式获取数据，仅在满足fragment可见和视图已经准备好的时候调用一次
     */
    protected abstract void lazyFetchData();

    protected abstract int getLayoutId();

    protected abstract void initView(View view);

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        isViewPrepared = true;
        lazyFetchDataIfPrepared();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mLlProgressBar = getView().findViewById(R.id.ll_progress_bar);
        ImageView img = getView().findViewById(R.id.img_progress);
        // 加载动画
        mAnimationDawable = (AnimationDrawable) img.getDrawable();
        // 默认进入页面就开启动画
        if (!mAnimationDawable.isRunning()) {
            mAnimationDawable.start();
        }
        mLlErrorRefresh = getView().findViewById(R.id.ll_error_refresh);
        // 点击加载失败布局
        mLlErrorRefresh.setOnClickListener(new PerfectClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                showLoading();
                lazyFetchDataIfPrepared();
            }
        });

        if (this.presenter == null) {
            //创建P层
            this.presenter = createPresenter();
        }

        if (this.view == null) {
            //创建V层
            this.view = createView();
        }
        //判定是否为空
        if (this.presenter == null) {
            throw new NullPointerException("presneter不能够为空");
        }
        if (this.view == null) {
            throw new NullPointerException("view不能够为空");
        }
        //绑定
        this.presenter.attachView(this.view);

        if (getUserVisibleHint() && !hasFetchData && isViewPrepared) {
            hasFetchData = true;
            lazyFetchDataIfPrepared();
        }
    }

    protected void showLoading() {
        hasFetchData = false;
        if (mLlProgressBar.getVisibility() != View.VISIBLE) {
            mLlProgressBar.setVisibility(View.VISIBLE);
        }
        //开始动画
        if (!mAnimationDawable.isRunning()) {
            mAnimationDawable.start();
        }

        if (mLlErrorRefresh.getVisibility() != View.GONE) {
            mLlErrorRefresh.setVisibility(View.GONE);
        }
    }

    /**
     * 加载完成的状态
     */
    protected void showContentView() {
        if (mLlProgressBar.getVisibility() != View.GONE) {
            mLlProgressBar.setVisibility(View.GONE);
        }
        // 停止动画
        if (mAnimationDawable.isRunning()) {
            mAnimationDawable.stop();
        }
        if (mLlErrorRefresh.getVisibility() != View.GONE) {
            mLlErrorRefresh.setVisibility(View.GONE);
        }
        if (rootView.getVisibility() != View.VISIBLE) {
            rootView.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 加载失败点击重新加载的状态
     */
    protected void showError() {
        if (mLlProgressBar.getVisibility() != View.GONE) {
            mLlProgressBar.setVisibility(View.GONE);
        }
        // 停止动画
        if (mAnimationDawable.isRunning()) {
            mAnimationDawable.stop();
        }
        if (mLlErrorRefresh.getVisibility() != View.VISIBLE) {
            mLlErrorRefresh.setVisibility(View.VISIBLE);
        }
        if (rootView.getVisibility() != View.GONE) {
            rootView.setVisibility(View.GONE);
        }
    }

    protected <T extends View> T getView(int id) {
        return (T) getView().findViewById(id);
    }

    protected abstract V createView();

    protected abstract P createPresenter();

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        this.presenter.detachView();
        unbinder.unbind();
    }

    public int getStatusHeight() {
        int statusBarHeight1 = -1;
        //获取status_bar_height资源的ID
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            //根据资源ID获取响应的尺寸值
            statusBarHeight1 = getResources().getDimensionPixelSize(resourceId);
        }
        return statusBarHeight1;
    }
}
