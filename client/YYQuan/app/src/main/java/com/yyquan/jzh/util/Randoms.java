package com.yyquan.jzh.util;

import java.util.Random;
import java.util.concurrent.BlockingDeque;
 

public class Randoms {

	public static String getrandomint(int strLength){
		Random rm=new Random();
		//获得随机数
        double pross = (1 + rm.nextDouble()) * Math.pow(10, strLength);
       // 将获得的获得随机数转化为字符串
        String fixLenthString = String.valueOf(pross);
 
        // 返回固定的长度的随机数
        return fixLenthString.substring(1, strLength + 1);
	}
	
	public static String getRandomString(int length){
		//随机字符串的随机字符库
	    String KeyString = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
	    StringBuffer sb = new StringBuffer();
	    int len = KeyString.length();
	    for (int i = 0; i < length; i++) {
	       sb.append(KeyString.charAt((int) Math.round(Math.random() * (len - 1))));
	    }
	    return sb.toString();
	}
	
	//这是随机数字产生器
	public static String getRandomNum(int length){
        //随机字符串的随机字符库
        String KeyString = "0123456789";
        StringBuffer sb = new StringBuffer();
        int len = KeyString.length();
        for (int i = 0; i < length; i++) {
           sb.append(KeyString.charAt((int) Math.round(Math.random() * (len - 1))));
        }
        return sb.toString();
    }
	
}
