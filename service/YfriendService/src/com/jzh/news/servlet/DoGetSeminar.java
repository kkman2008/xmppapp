package com.jzh.news.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.joda.time.DateTime;

import net.sf.json.JSONArray;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.jzh.news.dao.News_pinglunDaoImpl;
import com.jzh.news.dao.QuestionDaoImpl;
import com.jzh.news.dao.SeminarDaoImpl;
import com.jzh.news.dao.SeminarDicussCommentDaoImpl;
import com.jzh.news.dao.SeminarDiscussProccessDaoImpl;
import com.jzh.news.entity.tb_problem;
import com.jzh.news.entity.tb_theme;
import com.jzh.news.entity.tb_topicforumprocess;
import com.jzh.news.entity.tb_topicprocessuserpraise;
 

@WebServlet("/DoGetSeminar")
public class DoGetSeminar extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("开始查询研讨主题...");
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		String action = request.getParameter("action");
		String questionlisttype = request.getParameter("type"); 	
		String discusstype = request.getParameter("discusstype"); 
		String phase = request.getParameter("phase"); 	
		String topicid = request.getParameter("topicid"); 	
		String speechmode = request.getParameter("speechmode");
		SeminarDaoImpl seminardao = new SeminarDaoImpl(); 	
		SeminarDiscussProccessDaoImpl discussdaoimpl = new SeminarDiscussProccessDaoImpl();
		SeminarDicussCommentDaoImpl commentdaoimpl =  new SeminarDicussCommentDaoImpl();
		JSONObject array = new JSONObject();
		PrintWriter out = response.getWriter();
		
		switch(action) {
		case "update_seminar_topic": 
			seminardao.UpdateSeminarTopicPhase(topicid, phase, speechmode);
			break;
		case "get_seminartopic_list": 
			array.put("code", "success");
			array.put("msg", "有研讨主题数据");
			List<tb_theme> list = seminardao.getListbyType(questionlisttype);  
			array.put("data", list); 
			String str = JSONObject.toJSONString(array,SerializerFeature.WriteMapNullValue); 
			System.out.println(str);
			out.print(str);
			break;
		// 研讨主题讨论	
		case "create_seminar_topic_discuss":
			String jsonmodel = request.getParameter("seminardiscuss"); 
			tb_topicforumprocess seminardiscuss = (tb_topicforumprocess)JSON.parseObject(jsonmodel, tb_topicforumprocess.class);
			discussdaoimpl.CreateQuestion(seminardiscuss);
			break;
		case "get_seminar_discuss_list":	
			array.put("code", "success");
			array.put("msg", "有研讨主题讨论数据");
			List<tb_topicforumprocess> discusslist = discussdaoimpl.getListbyType(discusstype);  
			array.put("data", discusslist); 
			String discussstr = JSONObject.toJSONString(array,SerializerFeature.WriteMapNullValue);  
			out.print(discussstr);	
		// 研讨主题讨论表态
		case "create_discuss_comment":
			String jsonmodelcomment = request.getParameter("discusscomment"); 
			tb_topicprocessuserpraise topicprocessuserpraise = (tb_topicprocessuserpraise)JSON.parseObject(jsonmodelcomment, tb_topicprocessuserpraise.class);
			commentdaoimpl.CreateDicussComment(topicprocessuserpraise);
			break;
		case "get_comment_list":	
			array.put("code", "success");
			array.put("msg", "有研讨主题讨论表态数据");
			String topicprocessid = request.getParameter("topicprocessid"); 
			List<tb_topicprocessuserpraise> commentlist = commentdaoimpl.getListbyTopicProcessID(topicprocessid);  
			array.put("data", commentlist); 
			String discussstr1 = JSONObject.toJSONString(array,SerializerFeature.WriteMapNullValue);  
			out.print(discussstr1);	
		default:
			break;
		}
		 
	}
}