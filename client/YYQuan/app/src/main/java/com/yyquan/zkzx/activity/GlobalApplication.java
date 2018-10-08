package com.yyquan.zkzx.activity;

import android.app.Application;
import android.util.Log;

import com.yyquan.zkzx.entity.tb_user;


/**
 * Created by jzh on 2015/10/7.
 */
public class GlobalApplication extends Application {

    private static final String VALUE = "123456";
    private String value;
    private int appVersionValue;
    private String serverIP ="169.254.254.254";
    //private String serverIP ="169.254.254.254";
    private Long ServerPort;
    // 存储用户信息，全局可用
    public tb_user globalUser;
    public String getIfURL() {
        return ifURL;
    }

    public void setIfURL(String ifURL) {
        this.ifURL = ifURL;
    }

    public String ifURL ="http://" +serverIP+":8080";
    public String ip_icon = ifURL + "/YfriendService/DoGetIcon?name=";
    public String ip_user_message = ifURL + "/YfriendService/DoGetUser";
    public String ip_user_message_pro = ifURL + "/YfriendService/DoGetUserPro";
    public String ip_user_status = ifURL + "/YfriendService/DoGetLunTan";
    public String ip_dservlet = ifURL + "/YfriendService/Dservlet";
    public String ip_upload = ifURL + "/YfriendService/UploadServlet";
    public String ip_question = ifURL + "/YfriendService/DoGetQuestion";
    public String ip_user_question = ifURL + "/YfriendService/DoGetQuestion";
    public String ip_seminar_topic = ifURL + "/YfriendService/DoGetSeminar";
    public String ip_seminar_attendee = ifURL + "/YfriendService/DoGetSeminarArragement";
    public static final String  TAG = "GlobalApplication";

    public String InterfaceURL(String ServerIP, Long ServerPort) {
        ifURL = "http://" + ServerIP + ":" + ServerPort;
        setIfURL(ifURL);
        ip_icon = ifURL + "/YfriendService/DoGetIcon?name=";
        ip_user_message = ifURL + "/YfriendService/DoGetUser";
        ip_user_status = ifURL + "/YfriendService/DoGetLunTan";
        ip_dservlet = ifURL + "/YfriendService/Dservlet";
        ip_user_question = ifURL + "/YfriendService/DoGetQuestion";
        ip_seminar_topic = ifURL + "/YfriendService/DoGetSeminar";
        ip_seminar_attendee = ifURL + "/YfriendService/DoGetSeminarArragement";
        return ifURL;
    }
    public void InitialUser(tb_user user){
        this.globalUser = user;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        setValue(VALUE); // 初始化全局变量
        // get the version value from the first logo activity page
        Log.d(TAG, "onCreate: ");

    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public int getAppVersionValue() {
        return appVersionValue;
    }

    public void setAppVersionValue(int appVersionValue) {
        this.appVersionValue = appVersionValue;
    }

    public String getServerIP() {
        return serverIP;
    }

    public void setServerIP(String serverIP) {
        this.serverIP = serverIP;
    }

    public Long getServerPort() {
        return ServerPort;
    }

    public void setServerPort(Long serverPort) {
        ServerPort = serverPort;
    }
}
