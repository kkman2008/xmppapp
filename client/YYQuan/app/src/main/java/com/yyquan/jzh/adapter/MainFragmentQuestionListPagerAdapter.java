package com.yyquan.jzh.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

public class MainFragmentQuestionListPagerAdapter extends FragmentPagerAdapter {

    List<String> questionStateList; // question state for tab
    List<Fragment> childFragments;

    public MainFragmentQuestionListPagerAdapter(FragmentManager fm, List<String> questionStateList, List<Fragment> childFragments) {
        super(fm);
        this.questionStateList = questionStateList;
        this.childFragments = childFragments;
    }


    @Override
    public Fragment getItem(int position) {
        return childFragments.get(position);
    }

    @Override
    public int getCount() {
        return questionStateList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return questionStateList.get(position);
    }

}
