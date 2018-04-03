package com.shouxiu.rxjavaretrofit.mvp.home.contract;

import android.content.Context;

import com.shouxiu.rxjavaretrofit.api.HomeCateList;
import com.shouxiu.rxjavaretrofit.base.BaseModel;
import com.shouxiu.rxjavaretrofit.base.BasePresenter;
import com.shouxiu.rxjavaretrofit.base.BaseView;

import java.util.List;

import io.reactivex.Observable;

/**
 * @author yeping
 * @date 2018/4/3 16:12
 * TODO
 */

public interface HomeCateListContract {
    interface View extends BaseView{
        void getHomeAllList(List<HomeCateList> cateLists);
    }
    interface Model extends BaseModel {
        Observable getHomeCateList(Context context);
    }
    abstract class Presenter extends BasePresenter<View>{
        public abstract void getHomeCateList(Context context);
    }
}
