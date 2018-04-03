package com.shouxiu.rxjavaretrofit.base;

import android.content.Context;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;

/**
 * @创建者 yeping
 * @创建时间 2017/9/5 14:09
 * @描述 ${TODO}
 */

public abstract class BaseButton<V extends BaseView,P extends BasePresenter<V>> extends AppCompatButton {

    private P presenter;
    private V view;

    public P getPresenter() {
        return presenter;
    }

    public BaseButton(Context context) {
        super(context);
    }

    public BaseButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (this.presenter == null){
            //创建P层
            this.presenter = createPresenter();
        }

        if (this.view == null){
            //创建V层
            this.view = createView();
        }
        //判定是否为空
        if (this.presenter == null){
            throw new NullPointerException("presneter不能够为空");
        }
        if (this.view == null){
            throw new NullPointerException("view不能够为空");
        }
        //绑定
        this.presenter.attachView(this.view);
    }

    protected abstract V createView();

    protected abstract P createPresenter();

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.presenter.detachView();
    }
}
