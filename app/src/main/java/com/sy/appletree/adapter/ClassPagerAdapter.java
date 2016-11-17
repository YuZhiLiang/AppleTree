package com.sy.appletree.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by Administrator on 2016/10/27.
 */
public class ClassPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> mFragmentList;

    public void addFragment(List<Fragment> FragmentList) {
        mFragmentList.addAll(FragmentList);
        notifyDataSetChanged();
    }

    public ClassPagerAdapter(FragmentManager fm, List<Fragment> fragmentList) {
        super(fm);
        mFragmentList = fragmentList;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }
}
