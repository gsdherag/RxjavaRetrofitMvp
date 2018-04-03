package com.shouxiu.rxjavaretrofit.base;

/**
 * @author yeping
 * @date 2018/3/13 16:46
 * TODO
 */

public abstract class BasicActivity extends BaseActivity<BaseView, BasePresenter<BaseView>> implements BaseView {
    @Override
    protected BaseView createView() {
        return this;
    }

    @Override
    protected BasePresenter<BaseView> createPresenter() {
        return new BasePresenter<>();
    }
}
