package com.yyquan.zkzx.activity.SeminarTopic;
/*
 * Created by JHL, 2018/10/8
 */

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.squareup.picasso.Picasso;
import com.yyquan.zkzx.R;
import com.yyquan.zkzx.activity.BaseActivity;
import com.yyquan.zkzx.activity.GlobalApplication;
import com.yyquan.zkzx.view.CircleImageView;
import com.yyquan.zkzx.view.DialogView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

public class ActivityAttendeeDetail  extends BaseActivity  implements View.OnClickListener{
    @Bind(R.id.tb_expert)
    TableLayout tb_expert;
    @Bind(R.id.tb_listener)
    TableLayout tb_listener;
    @Bind(R.id.tb_assistant)
    TableLayout tb_assistant;
    @Bind(R.id.civ_host_image)
    CircleImageView civ_host_image;
    @Bind(R.id.tv_host_name)
    TextView tv_host_name;
    @Bind(R.id.tv_seminar_topic_count)
    TextView tv_seminar_topic_count;

    @Bind(R.id.civ_expert1)
    CircleImageView civ_expert1;
    @Bind(R.id.tv_expert1_name)
    TextView tv_expert1_name;
    @Bind(R.id.civ_expert2)
    CircleImageView civ_expert2;
    @Bind(R.id.tv_expert2_name)
    TextView tv_expert2_name;
    @Bind(R.id.civ_expert3)
    CircleImageView civ_expert3;
    @Bind(R.id.tv_expert3name)
    TextView tv_expert3_name;
    @Bind(R.id.civ_expert4)
    CircleImageView civ_expert4;
    @Bind(R.id.tv_expert4name)
    TextView tv_expert4_name;

    @Bind(R.id.civ_listener1)
    CircleImageView civ_listener1;
    @Bind(R.id.tv_listener1_name)
    TextView tv_listener1_name;
    @Bind(R.id.civ_listener2)
    CircleImageView civ_listener2;
    @Bind(R.id.tv_listener2name)
    TextView tv_listener2_name;
    @Bind(R.id.cv_listener3)
    CircleImageView civ_listener3;
    @Bind(R.id.tv_listener3name)
    TextView tv_listener3_name;
    @Bind(R.id.civ_listener4)
    CircleImageView civ_listener4;
    @Bind(R.id.tv_listener4name)
    TextView tv_listener4_name;


    @Bind(R.id.civ_assitant1)
    CircleImageView civ_assitant1;
    @Bind(R.id.tv_assitant1_name)
    TextView tv_assitant1_name;
    @Bind(R.id.civ_assitant2)
    CircleImageView civ_assitant2;
    @Bind(R.id.tv_assitant2name)
    TextView tv_assitant2_name;
    @Bind(R.id.rl_assitant2)
    RelativeLayout rl_assitant2;
    @Bind(R.id.rl_assitant3)
    RelativeLayout rl_assitant3;
    @Bind(R.id.civ_assitant3)
    CircleImageView civ_assitant3;
    @Bind(R.id.tv_assitant3name)
    TextView tv_assitant3_name;
    @Bind(R.id.rl_assitant4)
    RelativeLayout rl_assitant4;
    @Bind(R.id.civ_assitant4)
    CircleImageView civ_assitant4;
    @Bind(R.id.tv_assitant4name)
    TextView tv_assitant4_name;
    @Bind(R.id.iv_assitant_more)
    ImageView iv_assitant_more;
    @Bind(R.id.iv_expert_more)
    ImageView iv_expert_more;
    @Bind(R.id.rl_expert2)
    RelativeLayout rl_expert2;
    @Bind(R.id.rl_expert3)
    RelativeLayout rl_expert3;
    @Bind(R.id.rl_expert4)
    RelativeLayout rl_expert4;
    @Bind(R.id.rl_linster2)
    RelativeLayout rl_linster2;
    @Bind(R.id.rl_linster3)
    RelativeLayout rl_linster3;
    @Bind(R.id.rl_linster4)
    RelativeLayout rl_linster4;

    @Bind(R.id.tv_content_text)
    TextView tv_content_text;

    public String seminarAttendeeURL;
    public final String TAG = "ActivityAttendeeDetail";
    GlobalApplication application = new GlobalApplication();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DialogView.Initial(this, "正在评论......");
        DialogView.show();
        setContentView(R.layout.activity_attendee_detail);
        seminarAttendeeURL =application.ip_seminar_attendee;
        ButterKnife.bind(this);
        initialView();
        DialogView.dismiss();
    }

    @Override
    public void initialView() {
        BindData();
    }

    private  void BindData(){
        Intent intent=this.getIntent();
        String mSubjectID = intent.getStringExtra("ThemeSubjectID");
        tv_content_text.setText( intent.getStringExtra("ThemeContent"));
                // for test purpose
        if( mSubjectID == null){
            mSubjectID = "0931ce4b-41e5-4592-985d-074abb220781";
        }
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        params.put("themesubjectid", mSubjectID);
        params.put("action", "get_seminar_attendee");
        client.post(seminarAttendeeURL, params, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String str = new String(responseBody);
                if (str != null) {
                    Log.d(TAG, "onSuccess: str =" + str);
                    try {
                        JSONObject object = new JSONObject(str);
                        if (object.getString("code").equals("success")) {
                            JSONArray array = object.getJSONArray("data");
                            int discusscount = object.getInt("discusscount");
                            tv_seminar_topic_count.setText(discusscount + "条");
                            int listenerCount = 0;
                            int expertCount = 0;
                            int assistantCount = 0;
                            for (int i = 0; i < array.length(); i++) {
                                object = array.getJSONObject(i);
                                Log.d(TAG, "onSuccess: get user name" + object.getString("userName"));
                                String roleId = object.getString("roleId");
                                String iconurl = ((GlobalApplication) getApplication()).ip_icon + object.getString("headImagePath");
                                /*
                                    3	主持人
                                    4	旁听
                                    5	与会专家组
                                    6	助手
                                 */

                                if (roleId.equalsIgnoreCase("3") == true) {
                                    Picasso.with(ActivityAttendeeDetail.this).load(iconurl).resize(200, 200).centerInside().into(civ_host_image);
                                    tv_host_name.setText("主持人：" + object.getString("userName"));
                                }
                                    // 5. 与会专家
                                    if (roleId.equalsIgnoreCase("5") == true) {
                                        if (expertCount == 0) {
                                            Picasso.with(ActivityAttendeeDetail.this).load(iconurl).resize(200, 200).centerInside().into(civ_expert1);
                                            tv_expert1_name.setText(object.getString("userName"));
                                            expertCount++;
                                            continue;
                                        }
                                        if (expertCount == 1) {
                                            rl_expert2.setVisibility(View.VISIBLE);
                                            Picasso.with(ActivityAttendeeDetail.this).load(iconurl).resize(200, 200).centerInside().into(civ_expert2);
                                            tv_expert2_name.setText(object.getString("userName"));
                                            expertCount++;
                                            continue;
                                        }
                                        if (expertCount == 2) {
                                            rl_expert3.setVisibility(View.VISIBLE);
                                            Picasso.with(ActivityAttendeeDetail.this).load(iconurl).resize(200, 200).centerInside().into(civ_expert3);
                                            tv_expert3_name.setText(object.getString("userName"));
                                            expertCount++;
                                            continue;
                                        }
                                        if (expertCount == 3) {
                                            rl_expert4.setVisibility(View.VISIBLE);
                                            Picasso.with(ActivityAttendeeDetail.this).load(iconurl).resize(200, 200).centerInside().into(civ_expert4);
                                            tv_expert4_name.setText(object.getString("userName"));
                                            expertCount++;
                                            continue;
                                        }
                                        if (expertCount >= 4) {
                                            iv_expert_more.setVisibility(View.VISIBLE);
                                        }
                                }
                                // 4. 旁听
                                if (roleId.equalsIgnoreCase("4") == true) {
                                    if (listenerCount == 0) {
                                        Picasso.with(ActivityAttendeeDetail.this).load(iconurl).resize(200, 200).centerInside().into(civ_listener1);
                                        tv_listener1_name.setText(object.getString("userName"));
                                        listenerCount++;
                                        continue;
                                    }
                                    if (listenerCount == 1) {
                                        rl_linster2.setVisibility(View.VISIBLE);
                                        Picasso.with(ActivityAttendeeDetail.this).load(iconurl).resize(200, 200).centerInside().into(civ_listener2);
                                        tv_listener2_name.setText(object.getString("userName"));
                                        listenerCount++;
                                        continue;
                                    }
                                    if (listenerCount == 2) {
                                        rl_linster3.setVisibility(View.VISIBLE);
                                        Picasso.with(ActivityAttendeeDetail.this).load(iconurl).resize(200, 200).centerInside().into(civ_listener3);
                                        tv_listener3_name.setText(object.getString("userName"));
                                        listenerCount++;
                                        continue;
                                    }
                                    if (listenerCount == 3) {
                                        rl_linster4.setVisibility(View.VISIBLE);
                                        Picasso.with(ActivityAttendeeDetail.this).load(iconurl).resize(200, 200).centerInside().into(civ_listener4);
                                        tv_listener4_name.setText(object.getString("userName"));
                                        listenerCount++;
                                        continue;
                                    }
                                    if (listenerCount >= 4) {
                                        iv_assitant_more.setVisibility(View.VISIBLE);
                                    }
                                }
                                // 6 助手
                                if (roleId.equalsIgnoreCase("6") == true) {
                                    if (assistantCount == 0) {
                                        Picasso.with(ActivityAttendeeDetail.this).load(iconurl).resize(200, 200).centerInside().into(civ_assitant1);
                                        tv_assitant1_name.setText(object.getString("userName"));
                                        assistantCount++;
                                        continue;
                                    }
                                    if (assistantCount == 1) {
                                        rl_assitant2.setVisibility(View.VISIBLE);
                                        Picasso.with(ActivityAttendeeDetail.this).load(iconurl).resize(200, 200).centerInside().into(civ_assitant2);
                                        tv_assitant2_name.setText(object.getString("userName"));
                                        assistantCount++;
                                        continue;
                                    }
                                    if (assistantCount == 2) {
                                        rl_assitant3.setVisibility(View.VISIBLE);
                                        Picasso.with(ActivityAttendeeDetail.this).load(iconurl).resize(200, 200).centerInside().into(civ_assitant3);
                                        tv_assitant3_name.setText(object.getString("userName"));
                                        assistantCount++;
                                        continue;
                                    }
                                    if (assistantCount == 3) {
                                        rl_assitant4.setVisibility(View.VISIBLE);
                                        Picasso.with(ActivityAttendeeDetail.this).load(iconurl).resize(200, 200).centerInside().into(civ_assitant4);
                                        tv_assitant4_name.setText(object.getString("userName"));
                                        assistantCount++;
                                        continue;
                                    }
                                    if (assistantCount >= 4) {
                                        iv_expert_more.setVisibility(View.VISIBLE);
                                     }
                                  }
                            }
                            // after for each
                            if (listenerCount == 0) {
                                tb_listener.setVisibility(View.GONE);
                            }
                            if (expertCount == 0) {
                                tb_expert.setVisibility(View.GONE);
                            }
                            if (assistantCount == 0) {
                                tb_assistant.setVisibility(View.GONE);
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }


            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_discuss_back:
            finish();
            break;
            default:
                break;
        }

    }
}

/*
for test purpose, we get one seminar schedule

SELECT * from tb_scheduleplannerpro
inner join tb_discussiongroup
on tb_scheduleplannerpro.TaskID = tb_discussiongroup.TaskID
inner join tb_role
on tb_discussiongroup.RoleID = tb_role.RoleId
inner join tb_user
on tb_discussiongroup.UserID = tb_user.UserID
where tb_scheduleplannerpro.SubjectID = '0931ce4b-41e5-4592-985d-074abb220781'

 */