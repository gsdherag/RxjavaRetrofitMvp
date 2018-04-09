package com.shouxiu.rxjavaretrofit.view.home.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.shouxiu.rxjavaretrofit.api.bean.HomeCateList;
import com.shouxiu.rxjavaretrofit.view.home.fragment.OtherHomeFragment;
import com.shouxiu.rxjavaretrofit.view.home.fragment.RecommendHomeFragment;

import java.util.List;

/**
 * @author yeping
 * @date 2018/4/3 10:43
 * TODO
 */

public class HomeAllListAdapter extends FragmentStatePagerAdapter {

    private List<HomeCateList> mHomeCateLists;
    private String[] mTitle;
    private FragmentManager mFragmentManager;

    public HomeAllListAdapter(FragmentManager fm, List<HomeCateList> homeCateLists, String[] title) {
        super(fm);
        this.mFragmentManager = fm;
        this.mHomeCateLists = homeCateLists;
        this.mTitle = title;
    }

    @Override
    public int getCount() {
        return mTitle.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitle[position];
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new RecommendHomeFragment();
        }
        OtherHomeFragment otherHomeFragment = OtherHomeFragment.getInstance(mHomeCateLists.get(position - 1), position);
        return otherHomeFragment;
    }
}
