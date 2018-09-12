package com.yyquan.jzh.fragment.question;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.yyquan.jzh.R;
import com.yyquan.jzh.activity.GlobalApplication;
import com.yyquan.jzh.adapter.MainFragmentQuestionListPagerAdapter;
import com.yyquan.jzh.view.DialogView;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class QuestionListFragment extends Fragment {
    private View view;
    private TabLayout tablayout;
    private ViewPager mViewpager;
    private MainFragmentQuestionListPagerAdapter mPagerAdapter;

    private List<Fragment> childFragments;
    private  List<String> questionStateList;
    private  String url;

    @Override
    public View onCreateView(LayoutInflater infalter, ViewGroup container, Bundle saveInstance) {
        view = infalter.inflate(R.layout.fragment_question_list, container, false);
        initialView();
        url = ((GlobalApplication) getActivity().getApplication()).ifURL + "/YfriendService/DoGetType"; //???
        getQuestionTitle();
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
    private void getQuestionTitle() {
        DialogView.show();
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String str = new String(responseBody);
                if(str!= null ){
                    questionStateList = new ArrayList<>();
                    questionStateList.add("全部");
                    questionStateList.add("进行中");
                    questionStateList.add("未开始");
                    questionStateList.add("已结束");
                    initialData();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });

        DialogView.dismiss();
    }

    private void initialData() {
        childFragments = new ArrayList<Fragment>();
        for(int i =0 ; i< questionStateList.size(); i++){
           // [tbd] childFragments.add(QuestionTitleFragment.newInstance(questionStateList.get(i)));
        }
        mPagerAdapter = new MainFragmentQuestionListPagerAdapter(getChildFragmentManager(), questionStateList, childFragments);
        mViewpager.setAdapter(mPagerAdapter);
        tablayout.setupWithViewPager(mViewpager);
        tablayout.setTabMode(TabLayout.MODE_FIXED);
        DialogView.dismiss();
    }
}
