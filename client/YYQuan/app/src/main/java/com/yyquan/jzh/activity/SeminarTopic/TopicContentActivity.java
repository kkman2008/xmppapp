package com.yyquan.jzh.activity.SeminarTopic;
/*
 * Created by JHL, 2018/9/17
 * 研讨主题内容页，可参考NewsContentActivity
 * 从intent中获取seminar_item_content节点信息
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yyquan.jzh.R;
import com.yyquan.jzh.activity.GlobalApplication;
import com.yyquan.jzh.adapter.ContentFragmentPageAadpter;
import com.yyquan.jzh.entity.User;
import com.yyquan.jzh.entity.tb_theme;
import com.yyquan.jzh.fragment.seminartopic.DiscussListviewFragement;
import com.yyquan.jzh.fragment.seminartopic.SeminarTopicContentFragment;
import com.yyquan.jzh.location.Location;
import com.yyquan.jzh.view.DialogView;

import java.util.ArrayList;

public class TopicContentActivity extends FragmentActivity implements View.OnClickListener, ViewPager.OnPageChangeListener {

    private EditText et_discuss;
    private TextView tv_sumbitdiscuss;
    public TextView tv_discuss;

    private LinearLayout back;
    // 主题内容与主题讨论之间的切换
    private SeminarTopicContentFragment seminarTopicContentFragment;
    private DiscussListviewFragement discussListviewFragement;
    //定义一个ViewPager容器
    private ViewPager mPager;
    private ArrayList<Fragment> fragmentsList;
    private ContentFragmentPageAadpter mAdapter;
    //定义FragmentManager对象
    public FragmentManager fManager;
    Intent intent;
    public User user;
    public tb_theme content;
    String url  ;
    InputMethodManager imm;//键盘管理器

    Location lt;
    public int pl_size;
    int page_select;
    public String content_url = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        intent = getIntent();
        user = (User) intent.getSerializableExtra("user");
        content = (tb_theme) intent.getSerializableExtra("seminar_item_content");
        content_url = intent.getStringExtra("url");
        //  Toast.makeText(this,user.getNickname(),Toast.LENGTH_SHORT).show();
        setContentView(R.layout.activity_seminar_content);
        initialView();
        url = ((GlobalApplication) getApplication()).ifURL + "/YfriendService/DoGetPingLun";
        lt = new Location(this);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        lt.stopLocation();
    }

    /**
     * 初始化控件
     */
    private void initialView() {
        initViewPager();
        DialogView.Initial(this, "正在评论......");
        imm = (InputMethodManager) getSystemService(this.INPUT_METHOD_SERVICE);
        mPager = (ViewPager) findViewById(R.id.discuss_content_vPager);
        mPager.setOnPageChangeListener(this);

        et_discuss = (EditText) findViewById(R.id.news_content_editText_pinglun);
        tv_discuss = (TextView) findViewById(R.id.news_content_text_showpinglun);
        tv_sumbitdiscuss= (TextView) findViewById(R.id.news_content_text_enterpinglun);
        tv_discuss.setText(""+ "讨论");

        back = (LinearLayout) findViewById(R.id.news_content_back);
        back.setOnClickListener(this);
        tv_discuss.setOnClickListener(this);
        tv_sumbitdiscuss.setOnClickListener(this);
        mPager.setAdapter(mAdapter);
        mPager.setCurrentItem(0);
    }

    /**
     * 初始化viewpager
     */
    private void initViewPager() {
        fManager = getSupportFragmentManager();

        if (seminarTopicContentFragment == null) {
            seminarTopicContentFragment = new SeminarTopicContentFragment();
        }
        if ( discussListviewFragement  == null) {
            discussListviewFragement = new DiscussListviewFragement();
        }

        fragmentsList = new ArrayList<Fragment>();
        fragmentsList.add(seminarTopicContentFragment);
        fragmentsList.add(discussListviewFragement);
        mAdapter = new ContentFragmentPageAadpter(fManager, fragmentsList);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onClick(View v) {

    }
}
