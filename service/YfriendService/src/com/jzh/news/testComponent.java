package com.jzh.news;

import java.util.Date;

import com.jzh.news.dao.QuestionDaoImpl;
import com.jzh.news.entity.User;
import com.jzh.news.entity.tb_problem; 
import com.jzh.news.xmpp.XmppTool;

public class testComponent {

	public static void main(String[] args) { 
//		try{
//		System.out.println("start to test...");
//		User user = new User();
//		user.setUser("13810721823");
//		user.setPassword("123456");
//		XmppTool.create(user);
//		//Thread.sleep(5*1000);
//		//XmppTool.login("admin", "admin");
//		}catch(Exception e){
//			e.printStackTrace();
//		}
		tb_problem model = new tb_problem();
//		model.setUserid("1");
//		model.setQuestionid("7");
//		model.setProblemname("航天气动问题");
//		model.setIsdelete("0");
//		model.setProblemcontent("航天气动问题正文");
//		model.setAskthetime(new Date());
	    QuestionDaoImpl  daoi= new   QuestionDaoImpl();
	    //daoi.CreateQuestion(model);
	    model.setUserid("1");
		model.setQuestionid("7");
		model.setProblemname("航天气动问题");
		model.setIsdelete("0");
		model.setProblemcontent("航天气动问题正文");
		model.setAskthetime(new Date());
		model.setPhase(2);
	    daoi.UpdateQuestion(model);
	}

}
