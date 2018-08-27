package com.yyquan.jzh.util;

import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;

public class SmsComm {

    // @Autowired
    // private SmsService smsService;
    public static String TAG = "SmsComm";
    // 发送短信验证码，并保存短信
    public static boolean sendsms(String phone, String checkNumber) throws UnsupportedEncodingException {
        String checknum =checkNumber; // Randoms.getRandomNum(6);
        String smscontent = "尊敬的数智团（大数据&AI生态共享平台）会员，您本次操作的验证码是：" + checknum + "";
        //Log.e(TAG, "系统的验证码是：" + smscontent);
        // 发送消息
        SmsHttp smsHttp = new SmsHttp();
        String result = smsHttp.SendSms(phone, smscontent);
        String[] resultinfo = result.split("/");
        List<String> resultlist = Arrays.asList(resultinfo);
        // 如果发送成功
        if (resultlist.get(0).equals("000")) {
            // 存入到session里，session中名字为电话号码，值为验证码
            return true;
        } else {
            return false;
        }
    }

    public static void main(String[] args) {
        String result = "000/Send:1/Consumption:.1/Tmoney:101.6/sid:0718120135103349";
        String[] resultinfo = result.split("/");
        List<String> resultlist = Arrays.asList(resultinfo);
        //Log.e(TAG, "返回的list是" + resultlist.get(0) + " 第二个" + resultlist.get(2) + " 第三个" + resultlist.get(3));
    }

}
