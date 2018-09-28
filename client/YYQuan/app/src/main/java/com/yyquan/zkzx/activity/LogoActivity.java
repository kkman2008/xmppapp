package com.yyquan.zkzx.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.tencent.android.tpush.XGIOperateCallback;
import com.tencent.android.tpush.XGPushManager;
import com.yyquan.zkzx.R;
import com.yyquan.zkzx.entity.User;
import com.yyquan.zkzx.entity.tb_user;
import com.yyquan.zkzx.util.SaveUserUtil;
import com.yyquan.zkzx.util.SharedPreferencesUtil;
import com.yyquan.zkzx.view.LockView.LockActivity;
import com.yyquan.zkzx.xmpp.XmppService;
import com.yyquan.zkzx.xmpp.XmppTool;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class LogoActivity extends Activity {

    boolean bool_login;
    boolean bool_lock;
    private String url ;
    private final int AUTO_LOGIN = 1;
    private final int XMPP_LOGIN = 3;
    public  static String TAG = "LogoActivity";

    TextView tvAppVersion ;
    private GlobalApplication application;
     TextView tvServerIP;
     TextView tvServerPort;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try
        {
            super.onCreate(savedInstanceState);
            application = (GlobalApplication)getApplication();
            setContentView(R.layout.activity_logo);
            tvAppVersion = (TextView) findViewById( R.id.tv_app_version);
            tvServerIP =(TextView) findViewById( R.id.tv_server_ip);
            tvServerPort = (TextView) findViewById( R.id.tv_server_port);
            String appVersion = tvAppVersion.getText().toString().trim();
            // save to local to tracking and set to the global value too
            if("1".equalsIgnoreCase(appVersion)){
                SharedPreferencesUtil.setInt(this,"AppVersion", "AppVersion",1);
                application.setAppVersionValue(1);
            }else{
                SharedPreferencesUtil.setInt(this,"AppVersion", "AppVersion",2);
                application.setAppVersionValue(2);
            }
            // if the server configuration didn't be configured by user retrieve from default setting
            String savedServerIP = SharedPreferencesUtil.getString(this, "IPAddress", "IPAddress");
            String savedServerPort =SharedPreferencesUtil.getString(this, "IPAddress", "Port");
            if( savedServerIP== null || "".equalsIgnoreCase(savedServerIP)) {
                application.setServerIP(tvServerIP.getText().toString().trim());
                SharedPreferencesUtil.setString(this, "IPAddress", "IPAddress",tvServerIP.getText().toString().trim());
            }else{
                application.setServerIP(savedServerIP.trim());
            }
            if( savedServerPort == null || "".equalsIgnoreCase(savedServerPort.trim())){
                application.setServerPort(Long.valueOf(  tvServerPort.getText().toString().trim()));
            }else{
                application.setServerPort(Long.valueOf(savedServerPort));
            }
            // initialize the server side interface url
            application.InterfaceURL(application.getServerIP(), application.getServerPort());
            bool_login = SharedPreferencesUtil.getBoolean(this, "user_message", "login");
            bool_lock = SharedPreferencesUtil.getBoolean(this, "user_message", "lock");
            thread.start();
        }catch (Exception e) {
            e.printStackTrace();
            Log.d(TAG, "onCreate: ");
        }
    }
    Thread thread = new Thread() {
        @Override
        public void run() {
            if (bool_lock) {
                Intent intent = new Intent(LogoActivity.this,
                        LockActivity.class);
                startActivity(intent);
                finish();
            } else {
                if("".equalsIgnoreCase( application.ifURL) == false) {
                    Message m = h.obtainMessage(AUTO_LOGIN);
                    h.sendMessage(m);
                }
            }
        }
    };

    //
    Handler h = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case AUTO_LOGIN:
                    is_auto_login();
                    break;


                case XMPP_LOGIN:
                    final User users = (User) msg.obj;
                    new Thread() {

                        @Override
                        public void run() {
                            boolean result = XmppTool.getInstance( ((GlobalApplication)getApplication()).getServerIP()).login(users.getUser(), users.getPassword(), LogoActivity.this);
                            if (result) {

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        startService(new Intent(LogoActivity.this, XmppService.class));
                                        Intent intent = new Intent(LogoActivity.this, MainActivity.class);
                                        // 在页面跳转之前， 把user信息放到tb_user里面，并作为Application全局变量
                                        intent.putExtra("user", users);
                                        regster_push(users.getUser());
                                        SaveUserUtil.saveAccount(LogoActivity.this, users);
                                        SharedPreferencesUtil.setBoolean(LogoActivity.this, "user_message", "login", true);
                                        startActivity(intent);
                                        finish();
                                    }
                                });
                            } else {

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {


                                        Toast.makeText(LogoActivity.this, "登陆失败,请重试", Toast.LENGTH_LONG).show();
                                    }
                                });

                            }
                        }

                    }.start();

                    break;


            }


        }
    };


    /**
     * 先判断有没有自动登录
     */
    private void is_auto_login(){
        try{
            // 切换登录界面前，logo界面停留
            Thread.sleep(2000);
        if (bool_login) {
            User user = SaveUserUtil.loadAccount(LogoActivity.this);
            login(user.getUser(), user.getPassword());
        } else {
            startActivity(new Intent(LogoActivity.this, LoginActivity.class));
            finish();
        }
        }catch (Exception e) {
            e.printStackTrace();
            Log.d(TAG, "onCreate: ");
        }
    }


    /**
     * 登录
     */
    private void login(String user, String password) {
        RequestParams params = new RequestParams();
        params.put("user", user);
        params.put("password", password);
        params.put("action", "login");
        AsyncHttpClient client = new AsyncHttpClient();
        url = application.ip_user_message_pro;
        Log.d(TAG, "login: url =" + url );
        System.out.println("user =" + user);
        System.out.println("password =" + password);
        System.out.println("action = login");
        client.post(url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String str = new String(responseBody);
                if (str != null) {
                    try {
                        JSONObject object = new JSONObject(str);
                        if (object.getString("code").equals("success")) {
                            object = object.getJSONObject("data");
                            tb_user userpro =  JSON.parseObject(object.toString(), tb_user.class);
                            application.InitialUser( userpro);

                            User user = new User();
                            user.setUser(userpro.getAccount());
                            user.setPassword(userpro.getPassword());
                            user.setQq(userpro.getQq());
                            user.setIcon(userpro.getHeadimagepath());
                            user.setNickname(userpro.getName());
                            user.setCity(userpro.getCity());
                            user.setSex(userpro.getSex());
                            user.setYears(userpro.getYears());
                            user.setQianming(userpro.getQianming());
                            regster_push(userpro.getAccount());

                            Message m = h.obtainMessage(XMPP_LOGIN);
                            m.obj = user;
                            h.sendMessage(m);

                        } else {
                            startActivity(new Intent(LogoActivity.this, LoginActivity.class));
                            finish();
                            Toast.makeText(LogoActivity.this, "账号或密码有误，请重新输入", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        startActivity(new Intent(LogoActivity.this, LoginActivity.class));
                        finish();
                    }
                } else {
                    startActivity(new Intent(LogoActivity.this, LoginActivity.class));
                    finish();
                }
            }


            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(LogoActivity.this, "网络连接失败，请查看网络设置", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(LogoActivity.this, LoginActivity.class));
                finish();
            }
        });
    }

    /**
     * 注册信鸽
     */
    private void regster_push(String user) {
        try {
            XGPushManager.registerPush(getApplicationContext(), user, new XGIOperateCallback() {
                @Override
                public void onSuccess(Object o, int i) {
                    // Toast.makeText(LogoActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFail(Object o, int i, String s) {
                    //  Toast.makeText(LogoActivity.this, "注册失败" + i + "\n" + s, Toast.LENGTH_SHORT).show();
                }
            });
        }catch (Exception e) {
            e.printStackTrace();
            Log.d(TAG, "onCreate: ");
        }
    }
}
