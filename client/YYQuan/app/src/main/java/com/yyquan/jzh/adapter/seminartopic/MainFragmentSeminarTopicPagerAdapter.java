package com.yyquan.jzh.adapter.seminartopic;
/*
 * Created by JHL, 2018/9/12
 */

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

public class MainFragmentSeminarTopicPagerAdapter extends FragmentPagerAdapter {

    List<String> seminarSatusStateList; // question state for tab
    List<Fragment> childFragments;

    public MainFragmentSeminarTopicPagerAdapter(FragmentManager fm, List<String> seminarSatusStateList, List<Fragment> childFragments) {
        super(fm);
        this.seminarSatusStateList = seminarSatusStateList;
        this.childFragments = childFragments;
    }

    @Override
    public Fragment getItem(int position) {
        return childFragments.get(position);
    }

    @Override
    public int getCount() {
        return seminarSatusStateList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return seminarSatusStateList.get(position);
    }

}