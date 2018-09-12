package com.yyquan.jzh.activity;
/*
 * Created by JHL, 2018/9/12
 */

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.yyquan.jzh.R;
import com.yyquan.jzh.fragment.question.QuestionTitleFragment;

public class ActivityUnitTest  extends FragmentActivity {
    private  QuestionTitleFragment mlistviewFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unit_test);
        SetFragment();
    }
    private void SetFragment(){
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        //        mlistviewFragment = ;
          transaction.replace(R.id.fg_content, new QuestionTitleFragment());
        //transaction.replace(R.id.fg_content, new PhoneRegsterFragment());
        transaction.commit();
    }

}
