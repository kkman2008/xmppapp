package com.yyquan.jzh.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.PopupWindow;

import com.amap.api.location.AMapLocalWeatherForecast;
import com.amap.api.location.AMapLocalWeatherListener;
import com.amap.api.location.AMapLocalWeatherLive;
import com.yyquan.jzh.R;
import com.yyquan.jzh.fragment.question.QuestionListFragment;
import com.yyquan.jzh.fragment.question.QuestionTitleFragment;

public class MainQuestionListActivity extends FragmentActivity implements View.OnClickListener, PopupWindow.OnDismissListener, AMapLocalWeatherListener {

    public void initialView() {

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // set the fragment to current view
        setContentView(R.layout.activity_question_main);
        SetFragment();
    }
    private QuestionListFragment mQuestionList;
    private QuestionTitleFragment mlistviewFragment;
    private void SetFragment(){
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        //mQuestionList = new QuestionListFragment();
        mlistviewFragment = new QuestionTitleFragment();
        transaction.replace(R.id.fg_content, mlistviewFragment);
        transaction.commit();
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onDismiss() {

    }

    @Override
    public void onWeatherLiveSearched(AMapLocalWeatherLive aMapLocalWeatherLive) {

    }

    @Override
    public void onWeatherForecaseSearched(AMapLocalWeatherForecast aMapLocalWeatherForecast) {

    }
}
