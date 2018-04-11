package com.shouxiu.rxjavaretrofit.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.inputmethod.InputMethodManager;

import com.shouxiu.rxjavaretrofit.net.callback.ILoadingProgress;

import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * @创建者 yeping
 * @创建时间 2017/9/5 13:34
 * @描述 ${TODO}
 */

public abstract class BaseActivity<V extends BaseView, P extends BasePresenter<V>> extends AppCompatActivity implements ILoadingProgress {

    private P presenter;

    private V view;

    private Unbinder mUnbinder;

    public P getPresenter() {
        return presenter;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (this.presenter == null) {
            this.presenter = createPresenter();
        }

        if (this.view == null) {
            this.view = createView();
        }

        if (this.presenter == null) {
            throw new NullPointerException("presenter不能为空");
        }

        if (this.view == null) {
            throw new NullPointerException("view不能为空");
        }

        this.presenter.attachView(this.view);

        if (getLayoutId() != 0) {
            //设置布局资源文件
            setContentView(getLayoutId());
            //注解绑定
            mUnbinder = ButterKnife.bind(this);
        }

        initView();
    }

    protected void initView() {

    }

    protected abstract int getLayoutId();

    protected abstract V createView();

    protected abstract P createPresenter();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.presenter.detachView();
        if (mUnbinder != null) {
            mUnbinder.unbind();
        }
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void dismissLoading() {

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

    public void hiddenInput() {
        try {
            ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
                    .hideSoftInputFromWindow(BaseActivity.this.getCurrentFocus()
                                    .getWindowToken(),
                            0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
