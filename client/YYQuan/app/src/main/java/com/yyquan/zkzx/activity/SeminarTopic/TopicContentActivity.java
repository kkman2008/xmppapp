package com.yyquan.zkzx.activity.SeminarTopic;
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
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestHandle;
import com.loopj.android.http.RequestParams;
import com.yyquan.zkzx.R;
import com.yyquan.zkzx.activity.GlobalApplication;
import com.yyquan.zkzx.adapter.ContentFragmentPageAadpter;
import com.yyquan.zkzx.entity.User;
import com.yyquan.zkzx.entity.tb_theme;
import com.yyquan.zkzx.entity.tb_topicforumprocess;
import com.yyquan.zkzx.fragment.seminartopic.DiscussListviewFragement;
import com.yyquan.zkzx.fragment.seminartopic.SeminarTopicContentFragment;
import com.yyquan.zkzx.location.Location;
import com.yyquan.zkzx.view.DialogView;
import com.yyquan.zkzx.activity.GlobalApplication;
import com.yyquan.zkzx.adapter.ContentFragmentPageAadpter;
import com.yyquan.zkzx.entity.User;
import com.yyquan.zkzx.entity.tb_theme;
import com.yyquan.zkzx.entity.tb_topicforumprocess;
import com.yyquan.zkzx.fragment.seminartopic.DiscussListviewFragement;
import com.yyquan.zkzx.fragment.seminartopic.SeminarTopicContentFragment;
import com.yyquan.zkzx.location.Location;
import com.yyquan.zkzx.view.DialogView;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import butterknife.Bind;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

import static com.alibaba.fastjson.serializer.SerializerFeature.WriteMapNullValue;

public class TopicContentActivity extends FragmentActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener, ViewPager.OnPageChangeListener {

    private EditText et_discuss;
    private TextView tv_contentdiscuss;
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
    @Bind(R.id.spn_discuss_type)
    public Spinner spinnerDicussType;
    private static final String[] paths ={  "  ",   "主要观点", "重要问题", "指标数据","结论"};
    Intent intent;
    public User user;
    public tb_theme content;
    String url  ;
    InputMethodManager imm;//键盘管理器

    Location lt;
    public int pl_size;
    int page_select;
    public String content_url = "";
    public static final String TAG = "TopicContentActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        intent = getIntent();
        user = (User) intent.getSerializableExtra("user");
        content = (tb_theme) intent.getSerializableExtra("seminar_item_content");
        content_url = intent.getStringExtra("url");
        //  Toast.makeText(this,user.getNickname(),Toast.LENGTH_SHORT).show();
        setContentView(R.layout.activity_seminar_content);
        ButterKnife.bind(this);
        initialView();
        url = ((GlobalApplication) getApplication()).ifURL + "/YfriendService/DoGetSeminar";
        System.out.println("url =" + url);
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
        tv_discuss = (TextView) findViewById(R.id.tv_update_discuss);
        tv_contentdiscuss= (TextView) findViewById(R.id.tv_switch_content_discuss);
        tv_discuss.setText(""+ "讨论");

        back = (LinearLayout) findViewById(R.id.news_content_back);
        back.setOnClickListener(this);
        tv_discuss.setOnClickListener(this);
        tv_contentdiscuss.setOnClickListener(this);
        mPager.setAdapter(mAdapter);
        mPager.setCurrentItem(0);
        // initial the spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(TopicContentActivity.this,
                android.R.layout.simple_spinner_item, paths);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDicussType.setAdapter(adapter);
        spinnerDicussType.setOnItemSelectedListener(this);
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
        page_select = position;
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_update_discuss:
                Log.d(TAG, "onClick: tv_update_discuss..." );
                // get the related content and update to db
                upDicuss();
                break;
            case R.id.tv_switch_content_discuss:
                Log.d(TAG, "onClick: tv_switch_content_discuss..." );
                if (page_select == 0) {
                    mPager.setCurrentItem(1);
                } else {
                    mPager.setCurrentItem(0);
                }
                break;
            default:
                break;
        }
    }
    public tb_topicforumprocess getModelFromUI(){
        tb_topicforumprocess discussmodel = new tb_topicforumprocess();
        discussmodel.setId(UUID.randomUUID().toString());
        discussmodel.setThemeid( content.getSubjectid());
        discussmodel.setUserid(user.getId());
        discussmodel.setUsername(user.getNickname());
        discussmodel.setDiscusscontent(et_discuss.getText().toString());
        discussmodel.setDiscusscontenttype( String.valueOf(  spinnerDicussType.getSelectedItemId() ));
        return discussmodel;
    }
    private void upDicuss(){
        String pinglun = et_discuss.getText().toString();
        if (pinglun.equals("")) {
            Toast.makeText( TopicContentActivity.this, "请先填写讨论内容", Toast.LENGTH_SHORT).show();
            return;
        }
        String discussType = spinnerDicussType.getSelectedItem().toString().trim();
        if(discussType.equals("")){
            Toast.makeText( TopicContentActivity.this, "请先选择研讨类别", Toast.LENGTH_SHORT).show();
            return;
        }
        DialogView.show();
        tv_discuss.setEnabled(false);
        Date date = new Date();
        long ptime = date.getTime();
        RequestParams params = new RequestParams();
        tb_topicforumprocess discussmodel =getModelFromUI();
        params.put("seminardiscuss", JSON.toJSONString(discussmodel, WriteMapNullValue));
        params.put("action", "create_seminar_topic_discuss");
        tb_topicforumprocess dicussmodel = new tb_topicforumprocess();
        AsyncHttpClient client = new AsyncHttpClient();
        RequestHandle post = client.post(url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String str = new String(responseBody);
                if (str != null) {
                    try {
                        Log.d(TAG, "onSuccess: " + "插入成功...");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
        DialogView.dismiss();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
