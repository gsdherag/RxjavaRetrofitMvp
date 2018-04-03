package com.shouxiu.rxjavaretrofit;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.widget.Toast;

import com.shouxiu.rxjavaretrofit.fragment.FollowFragment;
import com.shouxiu.rxjavaretrofit.fragment.HomeFragment;
import com.shouxiu.rxjavaretrofit.fragment.LiveFragment;
import com.shouxiu.rxjavaretrofit.fragment.UserFragment;
import com.shouxiu.rxjavaretrofit.fragment.VideoFragment;
import com.shouxiu.rxjavaretrofit.ui.NavigateTabBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MainActivity extends AppCompatActivity {

    private static final String TAG_PAGE_HOME = "首页";
    private static final String TAG_PAGE_LIVE = "直播";
    private static final String TAG_PAGE_VIDEO = "视频";
    private static final String TAG_PAGE_FOLLOW = "关注";
    private static final String TAG_PAGE_USER = "我的";
    protected Unbinder unbinder;
    @BindView(R.id.mainTabBar)
    NavigateTabBar mainTabBar;
    //    退出时间
    private long exitTime = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        unbinder = ButterKnife.bind(this);
        mainTabBar.onRestoreInstanceState(savedInstanceState);
        mainTabBar.addTab(HomeFragment.class, new NavigateTabBar.TabParam(R.drawable.home_pressed, R.drawable
                .home_selected, TAG_PAGE_HOME));
        mainTabBar.addTab(LiveFragment.class, new NavigateTabBar.TabParam(R.drawable.live_pressed, R.drawable
                .live_selected, TAG_PAGE_LIVE));
        mainTabBar.addTab(VideoFragment.class, new NavigateTabBar.TabParam(R.drawable.video_pressed, R
                .drawable.video_selected, TAG_PAGE_VIDEO));
        mainTabBar.addTab(FollowFragment.class, new NavigateTabBar.TabParam(R.drawable.follow_pressed,
                R.drawable.follow_selected, TAG_PAGE_FOLLOW));
        mainTabBar.addTab(UserFragment.class, new NavigateTabBar.TabParam(R.drawable.user_pressed, R.drawable
                .user_selected, TAG_PAGE_USER));
        mainTabBar.setTabSelectListener(holder -> {
            switch (holder.tag.toString()) {
                case TAG_PAGE_HOME:
                    mainTabBar.showFragment(holder);
                    break;
                case TAG_PAGE_LIVE:
                    mainTabBar.showFragment(holder);
                    break;
                case TAG_PAGE_VIDEO:
                    mainTabBar.showFragment(holder);
                    break;
                case TAG_PAGE_FOLLOW:
                    mainTabBar.showFragment(holder);
                    break;
                case TAG_PAGE_USER:
                    if (mainTabBar != null)
                        mainTabBar.showFragment(holder);
                    break;
            }
        });
    }

    /**
     * 拦截返回键，要求点击两次返回键才退出应用
     *
     * @param keyCode 按键代码
     * @param event   点击事件
     * @return 是否处理本次事件
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void exit() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            Toast.makeText(getApplicationContext(), "再按一次退出程序",
                    Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            finish();
            System.exit(0);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (unbinder != null) {
            unbinder.unbind();
        }
    }

    /**
     * 保存数据状态
     *
     * @param outState
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mainTabBar.onSaveInstanceState(outState);
    }
}
