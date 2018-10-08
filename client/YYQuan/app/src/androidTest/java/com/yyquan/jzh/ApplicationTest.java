package com.yyquan.jzh;

import android.app.Application;
import android.test.ApplicationTestCase;
import android.util.Log;

import com.vdurmont.emoji.EmojiParser;

import org.junit.Test;

import java.io.FileInputStream;
import java.util.Properties;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    public ApplicationTest() {
        super(Application.class);
    }
    public  String TAG = "Unit Test:";

    @Test
    public void TestEmoji(){
        String string = "\uD83D\uDE03";
        string = EmojiParser.parseToAliases(string); //将表情符号转为字符
        System.out.println("string ="+string);
        string =  EmojiParser.parseToUnicode(string); //将字符转为表情符号
        System.out.println("string ="+string);
    }

    @Test
    public void unitTestStart(){
        Log.d(TAG, "unitTestStart: ");
        // retrieve the properties files
    }
    @Test
    public void getProPerties(){
//        properties = PropertiesUtil.getProperties(getApplicationContext());
//        url = properties.getProperty("serverUrl");
//        Log.i("URL", url);
        Log.d(TAG, "getProPerties starts...: ");

        try {
            /**
             * 从配置文件中读取数据 使用Java方式读取
             */
            Properties pro = new Properties();
            FileInputStream fis = new FileInputStream("assets/appConfig.properties");
            pro.load(fis);
           String url = pro.getProperty("serverUrl");
            Log.d(TAG, "getProPerties, url: " +  url);
        } catch (Exception e) {
            Log.e("读取资源文件异常",e.toString());
            e.printStackTrace();
        }
    }

}

