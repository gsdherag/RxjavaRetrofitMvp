package com.shouxiu.rxjavaretrofit.mvp.home.v;

import com.shouxiu.rxjavaretrofit.api.bean.HomeCateList;
import com.shouxiu.rxjavaretrofit.base.BaseView;

import java.util.List;

/**
 * @author yeping
 * @date 2018/4/3 16:12
 * TODO
 */

public interface HomeCateListView extends BaseView {

    void getHomeAllList(List<HomeCateList> cateLists);
}
