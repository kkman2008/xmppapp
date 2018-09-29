package com.jzh.news;

import java.io.File;
import java.util.Date;

import com.jzh.news.entity.tb_problem;
import com.jzh.news.util.LogFactory;
import com.jzh.news.util.LogUtil;

import java.util.logging.*;

import org.junit.Test;
public class testComponent { 
	
    // 自定义的全局log（个人一般用这个记录）
    private static java.util.logging.Logger log = LogFactory.getGlobalLog();
    // Jdk1.7以后自带的全局log（后面我添加了FileHandler，用于写入文件日志）
    private static Logger sysLog = Logger.getGlobal();

    static {
		//由于jdk自带的全局log没有写入文件的功能，我这里手动添加了文件handler
		LogUtil.addFileHandler(sysLog, Level.INFO, LogFactory.LOG_FOLDER + File.separator + "sys.log");
    }

	public static void main(String[] args) {
		  // 级别从上往下依次降低
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
		// model.setProblemname("航天气动问题");
		// model.setIsdelete("0");
		// model.setProblemcontent("航天气动问题正文");
		// model.setAskthetime(new Date());
		// daoi.CreateQuestion(model);
		model.setUserid("1");
		model.setQuestionid("7");
		model.setProblemname("航天气动问题");
		model.setIsdelete("0");
		model.setProblemcontent("航天气动问题正文");
		model.setAskthetime(new Date());
		model.setPhase(2);
		// daoi.UpdateQuestion(model); 
	}
	
	@Test
	public void TestStart(){
		System.out.println("TestStart...");
	}

}
