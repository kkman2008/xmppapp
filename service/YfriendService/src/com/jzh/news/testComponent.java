package com.jzh.news;

import java.io.File;
import java.util.Date;

import com.jzh.news.entity.tb_problem;
import com.jzh.news.util.LogFactory;
import com.jzh.news.util.LogUtil;

import java.util.logging.*;

import org.junit.Test;
public class testComponent { 
	
    // �Զ����ȫ��log������һ���������¼��
    private static java.util.logging.Logger log = LogFactory.getGlobalLog();
    // Jdk1.7�Ժ��Դ���ȫ��log�������������FileHandler������д���ļ���־��
    private static Logger sysLog = Logger.getGlobal();

    static {
		//����jdk�Դ���ȫ��logû��д���ļ��Ĺ��ܣ��������ֶ�������ļ�handler
		LogUtil.addFileHandler(sysLog, Level.INFO, LogFactory.LOG_FOLDER + File.separator + "sys.log");
    }

	public static void main(String[] args) {
		  // ��������������ν���
        log.severe("severe-->   this is severe!");
        log.warning("warning-->   this is warning!");
        log.info("info-->   this is info!");
        log.config("config-->   this is config!");
        log.fine("fine-->   this is fine!");
        log.finer("finer-->   this is finer!");
        log.finest("finest-->   this is finest!");
		// try{
		// System.out.println("start to test...");
		// User user = new User();
		// user.setUser("13810721823");
		// user.setPassword("123456");
		// XmppTool.create(user);
		// //Thread.sleep(5*1000);
		// //XmppTool.login("admin", "admin");
		// }catch(Exception e){
		// e.printStackTrace();
		// }
		tb_problem model = new tb_problem();
		// model.setUserid("1");
		// model.setQuestionid("7");
		// model.setProblemname("������������");
		// model.setIsdelete("0");
		// model.setProblemcontent("����������������");
		// model.setAskthetime(new Date());
		// daoi.CreateQuestion(model);
		model.setUserid("1");
		model.setQuestionid("7");
		model.setProblemname("������������");
		model.setIsdelete("0");
		model.setProblemcontent("����������������");
		model.setAskthetime(new Date());
		model.setPhase(2);
		// daoi.UpdateQuestion(model); 
	}
	
	@Test
	public void TestStart(){
		System.out.println("TestStart...");
	}

}
