package com.yyquan.jzh.activity;
/*
 * Created by JHL, 2018/9/12
 */

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.yyquan.jzh.R;
import com.yyquan.jzh.entity.User;
import com.yyquan.jzh.entity.tb_theme;
import com.yyquan.jzh.fragment.question.QuestionTitleFragment;
import com.yyquan.jzh.fragment.seminartopic.SeminarTopicContentFragment;

import java.util.Date;

public class ActivityUnitTest  extends FragmentActivity {
    private  QuestionTitleFragment mlistviewFragment;
    public User user;
    public tb_theme topicContent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unit_test);
        SetFragment();
        user = new User();
        user.setNickname("黄教授");
        user.setUser("13810721823");
        topicContent = new tb_theme();
        topicContent.setSubjectid("327e2fb7-2c3c-4d64-bbc5-0f00ae80d2bd");
        topicContent.setDisintegratortime(new Date());
        topicContent.setPhase(1);
        topicContent.setQuestionname("测试主题");
        topicContent.setQuestioncontent("主题内容...");
    }
    private void SetFragment(){
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        //        mlistviewFragment = ;
        //  transaction.replace(R.id.fg_content, new QuestionTitleFragment());
        //transaction.replace(R.id.fg_content, new PhoneRegsterFragment());
        //transaction.replace(R.id.fg_content, new SeminarTopicFragment());
       // transaction.replace(R.id.fg_content, new SeminarListFragment());
        //transaction.replace(R.id.fg_content, new DiscussListviewFragement());
        transaction.replace(R.id.fg_content, new SeminarTopicContentFragment());
        transaction.commit();
    }

}
