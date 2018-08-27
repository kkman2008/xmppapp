package com.jzh.news;

import com.jzh.news.entity.User;
import com.jzh.news.xmpp.XmppTool;

public class testComponent {

	public static void main(String[] args) { 
		try{
		System.out.println("start to test...");
		User user = new User();
		user.setUser("13810721823");
		user.setPassword("123456");
		XmppTool.create(user);
		//Thread.sleep(5*1000);
		//XmppTool.login("admin", "admin");
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
