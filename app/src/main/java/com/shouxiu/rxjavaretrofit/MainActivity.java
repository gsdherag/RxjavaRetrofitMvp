package com.shouxiu.rxjavaretrofit;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.shouxiu.rxjavaretrofit.api.HomeApi;
import com.shouxiu.rxjavaretrofit.api.HomeCateList;
import com.shouxiu.rxjavaretrofit.api.ParamsMapUtils;
import com.shouxiu.rxjavaretrofit.net.cache.XCCacheManager;
import com.shouxiu.rxjavaretrofit.net.callback.RxSubscriber;
import com.shouxiu.rxjavaretrofit.net.exception.ResponseThrowable;
import com.shouxiu.rxjavaretrofit.net.http.HttpUtils;
import com.shouxiu.rxjavaretrofit.net.transformer.DefaultTransformer;
import com.shouxiu.rxjavaretrofit.utils.GsonUtil;
import com.shouxiu.rxjavaretrofit.utils.NetworkUtil;

import java.util.List;

import static com.shouxiu.rxjavaretrofit.api.NetWorkApi.getHomeCateList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        mainTabBar.onRestoreInstanceState(savedInstanceState)
//        mainTabBar.addTab(HomeFragment::class.java, NavigateTabBar.TabParam(R.drawable.home_pressed, R.drawable.home_selected, TAG_PAGE_HOME))
//        mainTabBar.addTab(LiveFragment::class.java, NavigateTabBar.TabParam(R.drawable.live_pressed, R.drawable.live_selected, TAG_PAGE_LIVE))
//        mainTabBar.addTab(VideoFragment::class.java, NavigateTabBar.TabParam(R.drawable.video_pressed, R.drawable.video_selected, TAG_PAGE_VIDEO))
//        mainTabBar.addTab(FollowFragment::class.java, NavigateTabBar.TabParam(R.drawable.follow_pressed, R.drawable.follow_selected, TAG_PAGE_FOLLOW))
//        mainTabBar.addTab(UserFragment::class.java, NavigateTabBar.TabParam(R.drawable.user_pressed, R.drawable.user_selected, TAG_PAGE_USER))
//        mainTabBar.setTabSelectListener { holder ->
//                when (holder.tag.toString()) {
//            TAG_PAGE_HOME -> mainTabBar.showFragment(holder)
//            TAG_PAGE_LIVE -> mainTabBar.showFragment(holder)
//            TAG_PAGE_VIDEO -> mainTabBar.showFragment(holder)
//            TAG_PAGE_FOLLOW -> mainTabBar.showFragment(holder)
//            TAG_PAGE_USER -> if (mainTabBar != null) mainTabBar.showFragment(holder)
//        }
//        }
    }

    private void getHomeCateList() {
        HttpUtils.getInstance(this)
                .getRetrofitClient()
                .builder(HomeApi.class)
                .getHomeCateList(ParamsMapUtils.getDefaultParams())
                .compose(new DefaultTransformer<List<HomeCateList>>())  //预处理错误信息
                .subscribe(new RxSubscriber<List<HomeCateList>>() {     //订阅者响应类

                    @Override
                    protected String prepareCache() {
                        if (!NetworkUtil.isNetworkAvailable(MainActivity.this)) {
                            return XCCacheManager.getInstance(MainActivity.this).readCache(getHomeCateList);
                        }
                        return null;
                    }

                    @Override
                    public void onSuccess(List<HomeCateList> homeCateLists) {
                        String text = GsonUtil.object2Json(homeCateLists);
                        XCCacheManager.getInstance(MainActivity.this).writeCache(getHomeCateList, text);
                    }

                    @Override
                    public void onFail(ResponseThrowable t) {
                        Toast.makeText(MainActivity.this, t.getErrorMsg(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
