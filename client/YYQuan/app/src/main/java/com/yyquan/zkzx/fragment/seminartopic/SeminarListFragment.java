package com.yyquan.zkzx.fragment.seminartopic;
/*
 * Created by JHL, 2018/9/12
 * 研讨主题，列表页分四个选项卡viewpager
 */

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yyquan.zkzx.R;
import com.yyquan.zkzx.adapter.MainFragmentQuestionListPagerAdapter;
import com.yyquan.zkzx.view.DialogView;

import java.util.ArrayList;
import java.util.List;

public class SeminarListFragment  extends Fragment {
    private View view;
    private TabLayout tablayout;
    private ViewPager mViewpager;
    private MainFragmentQuestionListPagerAdapter mPagerAdapter;

    private List<Fragment> childFragments;
    private  List<String> seminarSatusStateList;
    private  String url;

    @Override
    public View onCreateView(LayoutInflater infalter, ViewGroup container, Bundle saveInstance) {
        view = infalter.inflate(R.layout.fragment_seminar_list, container, false);
        initialView();
       getSeminarTopic();
        return  view;
    }
    /**
     * 初始化控件
     */
    private void initialView() {
        tablayout = (TabLayout) view.findViewById(R.id.id_pageindicator);
        mViewpager = (ViewPager) view.findViewById(R.id.id_viewpager);
        //DialogView.Initial(getActivity(),"正在加载问题initialView......");
        //Toast.makeText(getActivity(),"正在加载问题initialView", Toast.LENGTH_LONG).show();
    }
    /*
     *
     */
    private void getSeminarTopic() {
        DialogView.show();
        seminarSatusStateList = new ArrayList<>();
        seminarSatusStateList.add("全部");
        seminarSatusStateList.add("进行中");
        seminarSatusStateList.add("未开始");
        seminarSatusStateList.add("已结束");
        initialData();
    }

    private void initialData() {
        childFragments = new ArrayList<Fragment>();
        for(int i =0 ; i< seminarSatusStateList.size(); i++){
            childFragments.add(SeminarTopicFragment.newInstance(seminarSatusStateList.get(i)));
        }
        mPagerAdapter = new MainFragmentQuestionListPagerAdapter(getChildFragmentManager(), seminarSatusStateList, childFragments);
        mViewpager.setAdapter(mPagerAdapter);
        tablayout.setupWithViewPager(mViewpager);
        tablayout.setTabMode(TabLayout.MODE_FIXED);
        DialogView.dismiss();
    }
}
