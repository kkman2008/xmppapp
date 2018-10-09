package com.yyquan.zkzx.activity.SeminarTopicComment;
/*
 * Created by JHL, 2018/10/9
 */

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.demievil.library.RefreshLayout;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestHandle;
import com.loopj.android.http.RequestParams;
import com.rockerhieu.emojicon.EmojiconEditText;
import com.rockerhieu.emojicon.EmojiconTextView;
import com.vdurmont.emoji.EmojiParser;
import com.yyquan.zkzx.R;
import com.yyquan.zkzx.activity.BaseActivity;
import com.yyquan.zkzx.activity.GlobalApplication;
import com.yyquan.zkzx.adapter.seminartopiccomment.CommentListViewAdapter;
import com.yyquan.zkzx.entity.tb_topicforumprocess;
import com.yyquan.zkzx.entity.tb_topicprocessuserpraise;
import com.yyquan.zkzx.entity.tb_user;
import com.yyquan.zkzx.view.CircleImageView;
import com.yyquan.zkzx.view.DialogView;
import com.yyquan.zkzx.xmpp.view.listview.MsgListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import butterknife.Bind;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

import static com.alibaba.fastjson.serializer.SerializerFeature.WriteMapNullValue;
public class ActivitySeminartopicComment extends BaseActivity implements View.OnClickListener {

    private LinearLayout commentsContentBack;
    private ImageView ivTopicReturn;
    private TextView textView3;
    private LinearLayout commentsContentShare;
    private CircleImageView listviewPinglunItemImageviewIcon;
    private TextView listviewPinglunItemTextViewName;
    private RelativeLayout rlTopicList;
    private TextView listviewPinglunItemTextViewLou;
    private TextView listviewPinglunItemTextViewLocation;
    private EmojiconTextView emotvTopicItemTextViewContent;
    private TextView listviewPinglunItemTextViewZan;
    private TextView listviewPinglunItemTextViewTime;
    private RefreshLayout fragmentContentSwipeContainer;
    private MsgListView mlvComment;
    private LinearLayout commentsContentLayoutEnterpinglun;
    private TextView tvPicture;
    private TextView tvCamera;
    private TextView tvVoice;
    private TextView tvEmotion;
    private EmojiconEditText eetTopicInput;
    private Button btnSpeak;
    private Button btnChatKeyboard;
    private TextView tvUpdateDiscuss;
    private RelativeLayout commentsContentLayoutPinglun;
    private TextView tvSwitchContentDiscuss;
    private FrameLayout emojicons;
    @Bind(R.id.tv_comment_count)
    TextView tv_comment_count;
    public final String TAG = "ActivityTopicComment";
    public  String url = "";
    String TopicProcessID ="";
    List <tb_topicprocessuserpraise> commentlist = new ArrayList<>();
    public GlobalApplication application = new GlobalApplication();
    CommentListViewAdapter mAdapter ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initialView();
    }
    @Override
    public void initialView() {
        //   topicProcessID = "f346f402-7b3b-4791-9211-10ec24047708";
        setContentView(R.layout.activity_seminar_topic_comment);
        ButterKnife.bind(this);
        BandControl();
        tvUpdateDiscuss.setOnClickListener(this);
        tv_comment_count.setOnClickListener(this);
        fragmentContentSwipeContainer.setChildView(mlvComment);
        BindPointData();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_update_discuss:
                CreateeSeminarTopicComment();
                break;
            default:
                break;
        }
    }
    public void CreateeSeminarTopicComment(){
        DialogView.show();
        tvUpdateDiscuss.setEnabled(false);
        Date date = new Date();
        long ptime = date.getTime();
        RequestParams params = new RequestParams();
        tb_topicprocessuserpraise discussmodel =getModelFromUI();
        params.put("jsonmodelcomment", JSON.toJSONString(discussmodel, WriteMapNullValue));
        params.put("action", "create_seminar_topic_comment");
        tb_topicforumprocess dicussmodel = new tb_topicforumprocess();
        AsyncHttpClient client = new AsyncHttpClient();
        //     http://localhost:8080/YfriendService/DoGetSeminar?action=get_seminar_discuss_list&discusstype=327e2fb7-2c3c-4d64-bbc5-0f00ae80d2bd
        url = application.ip_seminar_topic;
        RequestHandle post = client.post(url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String str = new String(responseBody);
                if (str != null) {
                    try {
                        tvUpdateDiscuss.setEnabled(true);
                        Log.d(TAG, "onSuccess: " + "插入成功...");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(ActivitySeminartopicComment.this, "网络连接失败", Toast.LENGTH_SHORT);
            }
        });
        DialogView.dismiss();
    }

    public tb_topicprocessuserpraise getModelFromUI(){
        tb_topicprocessuserpraise model = new tb_topicprocessuserpraise();
        model.setId(UUID.randomUUID().toString());
        model.setTopicprocessid(TopicProcessID);
        model.setComment(EmojiParser.parseToAliases(eetTopicInput.getText().toString()));

        model.setUserid(application.globalUser.getUserid());
        model.setUsername(application.globalUser.getName());
        model.setUsericon(application.globalUser.getHeadimagepath());
        model.setCommentpraisecount(0);
        return model;
    }

    private void BandControl(){
        commentsContentBack = (LinearLayout) findViewById(R.id.comments_content_back);
        ivTopicReturn = (ImageView) findViewById(R.id.iv_topic_return);
        textView3 = (TextView) findViewById(R.id.textView3);
        commentsContentShare = (LinearLayout) findViewById(R.id.comments_content_share);
        listviewPinglunItemImageviewIcon = (CircleImageView) findViewById(R.id.listview_pinglun_item_imageview_icon);
        listviewPinglunItemTextViewName = (TextView) findViewById(R.id.listview_pinglun_item_textView_name);
        rlTopicList = (RelativeLayout) findViewById(R.id.rl_topic_list);
        listviewPinglunItemTextViewLou = (TextView) findViewById(R.id.listview_pinglun_item_textView_lou);
        listviewPinglunItemTextViewLocation = (TextView) findViewById(R.id.listview_pinglun_item_textView_location);
        emotvTopicItemTextViewContent = (EmojiconTextView) findViewById(R.id.emotv_topic_item_textView_content);
        listviewPinglunItemTextViewZan = (TextView) findViewById(R.id.listview_pinglun_item_textView_zan);
        listviewPinglunItemTextViewTime = (TextView) findViewById(R.id.listview_pinglun_item_textView_time);
        fragmentContentSwipeContainer = (RefreshLayout) findViewById(R.id.fragment_content_swipe_container);
        mlvComment = (MsgListView) findViewById(R.id.mlv_comment);
        commentsContentLayoutEnterpinglun = (LinearLayout) findViewById(R.id.comments_content_layout_enterpinglun);
        tvPicture = (TextView) findViewById(R.id.tv_picture);
        tvCamera = (TextView) findViewById(R.id.tv_camera);
        tvVoice = (TextView) findViewById(R.id.tv_voice);
        tvEmotion = (TextView) findViewById(R.id.tv_emotion);
        eetTopicInput = (EmojiconEditText) findViewById(R.id.eet_topic_input);
        btnSpeak = (Button) findViewById(R.id.btn_speak);
        btnChatKeyboard = (Button) findViewById(R.id.btn_chat_keyboard);
        tvUpdateDiscuss = (TextView) findViewById(R.id.tv_update_discuss);
        commentsContentLayoutPinglun = (RelativeLayout) findViewById(R.id.comments_content_layout_pinglun);
        tvSwitchContentDiscuss = (TextView) findViewById(R.id.tv_switch_content_discuss);
        emojicons = (FrameLayout) findViewById(R.id.emojicons);

    }
    private  void BindPointData(){
        Intent intent=this.getIntent();
        TopicProcessID = intent.getStringExtra("ThemeSubjectID");
        // for test purpose
        if( TopicProcessID == null){
            TopicProcessID = "0931ce4b-41e5-4592-985d-074abb220781";
        }
        String SeminarPoint = intent.getStringExtra("ThemeContent");
        if( SeminarPoint == null)
        {
            SeminarPoint = "Default points";
        }
        if( application.globalUser ==null){
            application.globalUser  = new tb_user();
            application.globalUser.setUserid("b8e49cc5-77ca-a5ac-36e2-31261435bd13");
            application.globalUser.setName("王璐");
            application.globalUser.setHeadimagepath("icon1.jpg");
        }
        emotvTopicItemTextViewContent.setText(SeminarPoint );
        getData(TopicProcessID);
    }

    /**
     * 业务逻辑处理
     */
    private Handler h = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    tv_comment_count.setText(commentlist.size() + "人参与评论");
                        mAdapter  = new CommentListViewAdapter(ActivitySeminartopicComment.this, commentlist);
                        mlvComment.setAdapter(mAdapter);
                        fragmentContentSwipeContainer.setRefreshing(false);

                          break;
            }
        }
    };



    /**
     * 获取数据
     */
    private void getData( String topicProcessID){
        DialogView.show();
        tvUpdateDiscuss.setEnabled(false);
        Date date = new Date();
        long ptime = date.getTime();
        RequestParams params = new RequestParams();
         commentlist  = new ArrayList<>();
        params.put("topicprocessid",topicProcessID);
        params.put("action", "get_comment_list");
        tb_topicforumprocess dicussmodel = new tb_topicforumprocess();
        AsyncHttpClient client = new AsyncHttpClient();
        //     http://localhost:8080/YfriendService/DoGetSeminar?action=get_seminar_discuss_list&discusstype=327e2fb7-2c3c-4d64-bbc5-0f00ae80d2bd
        url = application.ip_seminar_topic;
        RequestHandle post = client.post(url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String str = new String(responseBody);
                if (str != null) {
                    try {
                        JSONObject object = new JSONObject(str);
                        if (object.getString("code").equals("success")) {
                            JSONArray array = object.getJSONArray("data");
                            commentlist = new ArrayList<tb_topicprocessuserpraise>(JSON.parseArray(array.toString(), tb_topicprocessuserpraise.class));
                            Message m = Message.obtain(h, 1); // 发送消息绑定数据
                            h.sendMessage(m);
                            Log.d(TAG, "onSuccess: " + "成功获得列表...");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(ActivitySeminartopicComment.this, "网络连接失败...", Toast.LENGTH_SHORT);
            }
        });
    }
}