package com.shouxiu.rxjavaretrofit.view.home.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.shouxiu.rxjavaretrofit.api.HomeCateList;
import com.shouxiu.rxjavaretrofit.fragment.LiveFragment;

import java.util.List;

/**
 * @author yeping
 * @date 2018/4/3 10:43
 * TODO
 */

public class HomeAllListAdapter extends FragmentStatePagerAdapter {

    private List<HomeCateList> mHomeCateLists;
    private String[] mTiltle;
    private FragmentManager mFragmentManager;

    public HomeAllListAdapter(FragmentManager fm, List<HomeCateList> homeCateLists, String[] title) {
        super(fm);
        this.mFragmentManager = fm;
        this.mHomeCateLists = homeCateLists;
        this.mTiltle = title;
    }

    @Override
    public int getCount() {
        return mTiltle.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTiltle[position];
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new LiveFragment();
        }
        LiveFragment otherHomeFragment = new LiveFragment();
        return otherHomeFragment;
    }
}
