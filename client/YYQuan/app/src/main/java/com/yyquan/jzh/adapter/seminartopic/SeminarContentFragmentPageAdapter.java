package com.yyquan.jzh.adapter.seminartopic;
/*
 * Created by JHL, 2018/9/17
 * viewpager的数据适配器
 */

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class SeminarContentFragmentPageAdapter extends FragmentPagerAdapter {
    private ArrayList<Fragment> fragmentArrayList;
    public  SeminarContentFragmentPageAdapter(FragmentManager fm) {super(fm);}
    public SeminarContentFragmentPageAdapter(FragmentManager fm, ArrayList<Fragment> fragments) {super(fm);
        this.fragmentArrayList = fragments;}
    @Override
    public Fragment getItem(int position) {
        return fragmentArrayList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentArrayList.size();
    }
}
