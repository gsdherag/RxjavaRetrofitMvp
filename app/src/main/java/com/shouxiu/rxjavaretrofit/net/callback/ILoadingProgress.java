package com.shouxiu.rxjavaretrofit.net.callback;

/**
 * @Created at :  2017/9/12.
 * @Version :  1.0
 * @Description :
 */

public interface ILoadingProgress {
    /**
     * 显示loading
     */
    void showLoading();

    /**
     * 隐藏loading
     */
    void dismissLoading();
}
