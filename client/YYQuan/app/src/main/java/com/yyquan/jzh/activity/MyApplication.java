package com.yyquan.jzh.activity;

import android.app.Application;



import java.util.concurrent.TimeUnit;


/**
 * Created by jzh on 2015/10/7.
 */
public class MyApplication extends Application {

    private static final String VALUE = "123456";

    private String value;

    @Override
    public void onCreate()
    {
        super.onCreate();
        setValue(VALUE); // 初始化全局变量
    }

    public void setValue(String value)
    {
        this.value = value;
    }

    public String getValue()
    {
        return value;
    }
}
