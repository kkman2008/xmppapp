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
import com.jzh.news.entity.tb_problem;
import com.jzh.news.entity.tb_theme;
 

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
		SeminarDaoImpl seminardao = new SeminarDaoImpl(); 	
		JSONObject array = new JSONObject();
		PrintWriter out = response.getWriter();
		
		switch(action) {
		case "update_seminar_topic": 
			break;
		case "get_seminartopic_list": 
			array.put("code", "success");
			array.put("msg", "有研讨主题数据");
			List<tb_theme> list = seminardao.getListbyType(questionlisttype);  
			array.put("data", list);
			//array.put("data", arrays.toString()); 
			String str = JSONObject.toJSONString(array,SerializerFeature.WriteMapNullValue);  
			out.print(str);
			break;
		default:
			break;
		}
		 
	}
}