package com.yyquan.zkzx.activity.SeminarTopic;
/*
 * Created by JHL, 2018/9/17
 * 研讨主题内容页，可参考NewsContentActivity
 * 从intent中获取seminar_item_content节点信息
 */

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestHandle;
import com.loopj.android.http.RequestParams;
import com.rockerhieu.emojicon.EmojiconGridFragment;
import com.rockerhieu.emojicon.EmojiconsFragment;
import com.rockerhieu.emojicon.emoji.Emojicon;
import com.vdurmont.emoji.EmojiParser;
import com.yyquan.zkzx.R;
import com.yyquan.zkzx.activity.GlobalApplication;
import com.yyquan.zkzx.adapter.ContentFragmentPageAadpter;
import com.yyquan.zkzx.common.Constants;
import com.yyquan.zkzx.entity.User;
import com.yyquan.zkzx.entity.tb_theme;
import com.yyquan.zkzx.entity.tb_topicforumprocess;
import com.yyquan.zkzx.fragment.seminartopic.DiscussListviewFragement;
import com.yyquan.zkzx.fragment.seminartopic.SeminarTopicContentFragment;
import com.yyquan.zkzx.location.Location;
import com.yyquan.zkzx.view.DialogView;
import com.yyquan.zkzx.xmpp.view.listview.MsgListView;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import butterknife.Bind;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

import static com.alibaba.fastjson.serializer.SerializerFeature.WriteMapNullValue;

public class TopicContentActivity extends FragmentActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener, ViewPager.OnPageChangeListener, EmojiconGridFragment.OnEmojiconClickedListener, EmojiconsFragment.OnEmojiconBackspaceClickedListener, MsgListView.IXListViewListener {

    @Bind(R.id.eet_topic_input)
    EditText eet_topic_input;
    private TextView tv_contentdiscuss;

    @Bind(R.id.tv_update_discuss)
    TextView tv_send_discuss;

    private LinearLayout back;
    // 主题内容与主题讨论之间的切换
    private SeminarTopicContentFragment seminarTopicContentFragment;
    private DiscussListviewFragement discussListviewFragement;
    //定义一个ViewPager容器
    private ViewPager TopicViwerPager;
    private ArrayList<Fragment> fragmentsList;
    private ContentFragmentPageAadpter mAdapter;
    //定义FragmentManager对象
    public FragmentManager fManager;
    @Bind(R.id.spn_discuss_type)
    public Spinner spinnerDicussType;
    @Bind(R.id.iv_topic_return)
    ImageView iv_topic_return;
    @Bind(R.id.tv_emotion)
    TextView tvemotion;
    @Bind(R.id.emojicons)
    FrameLayout emojicons;
    @Bind(R.id.btn_speak)
    Button btn_speak;
    @Bind(R.id.btn_chat_keyboard)
    Button btn_chat_keyboard;
    @Bind(R.id.tv_voice)
    TextView tv_voice;
    @Bind(R.id.tv_picture)
    TextView tv_picture;
    @Bind(R.id.tv_camera)
    TextView tv_camera;

    boolean bool = false;
    private static final String[] paths ={  "↓选择类别",   "主要观点", "重要问题", "指标数据","结论"};
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
        TopicViwerPager = (ViewPager) findViewById(R.id.discuss_content_vPager);
        TopicViwerPager.setOnPageChangeListener(this);

        tv_contentdiscuss= (TextView) findViewById(R.id.tv_switch_content_discuss);

        back = (LinearLayout) findViewById(R.id.news_content_back);
        back.setOnClickListener(this);
        tv_send_discuss.setOnClickListener(this);
        tv_contentdiscuss.setOnClickListener(this);
        TopicViwerPager.setAdapter(mAdapter);
        TopicViwerPager.setCurrentItem(1);
        // initial the spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(TopicContentActivity.this,
                android.R.layout.simple_spinner_item, paths);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDicussType.setAdapter(adapter);
        setEmojiconFragment(false);
        spinnerDicussType.setOnItemSelectedListener(this);
        iv_topic_return.setOnClickListener(this);
        tvemotion.setOnClickListener(this);
        eet_topic_input.setOnClickListener(this);
        btn_speak.setOnClickListener(this);
        btn_chat_keyboard.setOnClickListener(this);
        tv_voice.setOnClickListener(this);
        tv_picture.setOnClickListener(this);
        tv_camera.setOnClickListener(this);

        eet_topic_input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() < 1) {
                    tv_send_discuss.setEnabled(false);

                } else {
                    tv_send_discuss.setEnabled(true);
                }
            }
        });

    }


    private void setEmojiconFragment(boolean useSystemDefault) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.emojicons, EmojiconsFragment.newInstance(useSystemDefault))
                .commit();
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
                    TopicViwerPager.setCurrentItem(1);
                } else {
                    TopicViwerPager.setCurrentItem(0);
                }
                break;
            case R.id.iv_topic_return:
                Log.d(TAG, "onClick: iv_topic_return..." );
                finish();
                break;
            case R.id.tv_emotion:
                if (!bool) {
                    hintKbTwo();
                    emojicons.setVisibility(View.VISIBLE);
                    bool = true;
                } else {
                    emojicons.setVisibility(View.GONE);
                    bool = false;
                }
                Log.d(TAG, "onClick: tv_emotion..." );
                break;
            case R.id.eet_topic_input:
                if (bool) {
                    emojicons.setVisibility(View.GONE);
                    bool = false;
                }
                Log.d(TAG, "onClick: eet_topic_input..." );
                break;
            case R.id.btn_speak:
            case R.id.tv_voice:
                eet_topic_input.setVisibility(View.GONE);
                tv_send_discuss.setVisibility(View.GONE);
                btn_chat_keyboard.setVisibility(View.VISIBLE);
                btn_speak.setVisibility(View.VISIBLE);
                Log.d(TAG, "onClick: btn_speak");
                break;
            case R.id.btn_chat_keyboard:
                eet_topic_input.setVisibility(View.VISIBLE);
                tv_send_discuss.setVisibility(View.VISIBLE);
                btn_chat_keyboard.setVisibility(View.GONE);
                btn_speak.setVisibility(View.GONE);
                Log.d(TAG, "onClick: btn_chat_keyboard");
                break;
            case R.id.tv_camera:// 拍照
                selectImageFromCamera();
                break;
            case R.id.tv_picture:// 图片
                selectImageFromLocal();
                break;
            default:
                break;
        }
    }

    /**
     * 默认先上传本地图片，之后才显示出来 sendImageMessage
     *
     * @param local
     */
    private void sendImageMessage(String local) { 
         // [上传图片， 刷新列表]

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case Constants.REQUESTCODE_TAKE_CAMERA:// 当取到值的时候才上传path路径下的图片到服务器
                    Log.d(TAG, "本地图片的地址：" + localCameraPath);
                    sendImageMessage(localCameraPath);
                    break;
                case Constants.REQUESTCODE_TAKE_LOCAL:
                    if (data != null) {
                        Uri selectedImage = data.getData();
                        if (selectedImage != null) {
                            Cursor cursor = getContentResolver().query(
                                    selectedImage, null, null, null, null);
                            cursor.moveToFirst();
                            int columnIndex = cursor.getColumnIndex("_data");
                            String localSelectPath = cursor.getString(columnIndex);
                            cursor.close();
                            if (localSelectPath == null
                                    || localSelectPath.equals("null")) {
                                Log.d(TAG, "找不到您想要的图片");
                                return;
                            }
                            sendImageMessage(localSelectPath);
                        }
                    }
                    break;
            }
        }
    }


    /**
     * 选择图片
     */
    public void selectImageFromLocal() {
        Intent intent;
        if (Build.VERSION.SDK_INT < 19) {
            intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
        } else {
            intent = new Intent(
                    Intent.ACTION_PICK,
                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        }
        startActivityForResult(intent, Constants.REQUESTCODE_TAKE_LOCAL);
    }
    private String localCameraPath = "";// 拍照后得到的图片地址
    /**
     * 启动相机拍照 startCamera
     */
    public void selectImageFromCamera() {
        Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File dir = new File(Constants.CODER_PICTURE_PATH);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File file = new File(dir, String.valueOf(System.currentTimeMillis())
                + ".jpg");
        localCameraPath = file.getPath();
        Uri imageUri = Uri.fromFile(file);
        openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(openCameraIntent,
                Constants.REQUESTCODE_TAKE_CAMERA);
    }
    public tb_topicforumprocess getModelFromUI(){
        tb_topicforumprocess discussmodel = new tb_topicforumprocess();
        discussmodel.setId(UUID.randomUUID().toString());
        discussmodel.setThemeid( content.getSubjectid());
        discussmodel.setUserid(user.getId());
        discussmodel.setUsername(user.getNickname());
        discussmodel.setDiscusscontent(EmojiParser.parseToAliases(  eet_topic_input.getText().toString()));
        discussmodel.setDiscusscontenttype( String.valueOf(  spinnerDicussType.getSelectedItemId() ));
        return discussmodel;
    }
    private void upDicuss(){
        String pinglun = eet_topic_input.getText().toString();
        String discussType = spinnerDicussType.getSelectedItem().toString().trim();
        if(discussType.equals("↓选择类别")){
            Toast.makeText( TopicContentActivity.this, "请先选择研讨类别", Toast.LENGTH_SHORT).show();
            return;
        }
        if (pinglun.equals("")) {
            Toast.makeText( TopicContentActivity.this, "请先填写讨论内容", Toast.LENGTH_SHORT).show();
            return;
        }
        DialogView.show();
        tv_send_discuss.setEnabled(false);
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

    //此方法只是关闭软键盘
    private void hintKbTwo() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive() && getCurrentFocus() != null) {
            if (getCurrentFocus().getWindowToken() != null) {
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }

    @Override
    public void onEmojiconClicked(Emojicon emojicon) {
        EmojiconsFragment.input(eet_topic_input, emojicon);
    }

    @Override
    public void onEmojiconBackspaceClicked(View v) {

    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoadMore() {

    }
}
