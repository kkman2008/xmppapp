package com.yyquan.zkzx.util;

import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

import cz.msebera.android.httpclient.Header;


/**
 * 发送短信基础类
 * 
 * @author administration
 *
 */
public class SmsHttp {

    private String uid = "cstcbdai2018";
    private String pwd = "cstc2018";
    public static String TAG = "SmsHttp";
    public static String dateToString(Date time){
        SimpleDateFormat formatter;
        formatter = new SimpleDateFormat ("yyyy/MM/dd HH:mm:ss");
        String ctime = formatter.format(time);
        return ctime;
    }
    public String SendSms(String mobile, String content) throws UnsupportedEncodingException {
        HttpURLConnection httpconn = null;
        String result = "-20";
        StringBuilder sb = new StringBuilder();
        sb.append("http://service.winic.org:8009/sys_port/gateway/index.asp?");
        sb.append("id=").append(uid);
        sb.append("&pwd=").append(pwd);
        sb.append("&to=").append(mobile);
        sb.append("&content=").append(URLEncoder.encode(content, "GB2312"));
        sb.append("&time=");
        sb.append(URLEncoder.encode(dateToString(new Date()), "GB2312") );
        // time format yyyy/mm/dd hh:mm:ss
        //Log.d(TAG,  "接口url:"+sb.toString());
        try {
            URL url = new URL(sb.toString());
            httpconn = (HttpURLConnection) url.openConnection();
            httpconn.setRequestMethod("GET");
            BufferedReader rd = new BufferedReader(new InputStreamReader(httpconn.getInputStream()));
            result = rd.readLine();
            rd.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally
        {
            if (httpconn != null) {
                httpconn.disconnect();
                httpconn = null;
            }
        }
        return result;
    }

    public void SendSmsAsync(String phone, String content) throws UnsupportedEncodingException {
        String url =  "http://service.winic.org:8009/sys_port/gateway/index.asp";
        RequestParams params = new RequestParams();
        params.put("id", uid);
        params.put("pwd", pwd);
        params.put("to",phone);
        params.put("content", URLEncoder.encode(content, "GB2312"));
        params.put("time",URLEncoder.encode(dateToString(new Date()), "GB2312") );

        AsyncHttpClient client = new AsyncHttpClient();
        client.setConnectTimeout(5000);
        client.post(url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String str = new String(responseBody);
                if (str != null) {
                    Log.d(TAG, "onSuccess: ");
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d(TAG, "onFailure: ");
            }
            });
    }
    
    public static void main(String[]args) throws UnsupportedEncodingException{
        SmsHttp smsHttp=new SmsHttp();
        String info="你好，验证码是：cassee1234" + dateToString(new Date());
        //String wate=smsHttp.SendSms("13810721823",info);
        //Log.e(TAG, "这是返回值"+wate);
        smsHttp.SendSmsAsync("13810721823",info);
    }
    
    
    
}
