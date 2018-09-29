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
import com.jzh.news.entity.tb_problem;
 

@WebServlet("/DoGetQuestion")
public class DoGetQuestion extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("��ʼ��ѯ����...");
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		String action = request.getParameter("action");
		String questionlisttype = request.getParameter("type"); 			
		QuestionDaoImpl questiondao = new QuestionDaoImpl();		
		JSONObject array = new JSONObject();
		PrintWriter out = response.getWriter();
		
		switch(action) {
		case "create_question":
			String jsonmodel = request.getParameter("question");
			System.out.println( "create_question jsonmodel: " + jsonmodel);
			JSONObject obj = (JSONObject) JSON.parse(jsonmodel);
			tb_problem problem = JSON.parseObject(jsonmodel, tb_problem.class);

			questiondao.CreateQuestion(problem);
			break;
		case "get_question_list": 
			array.put("code", "success");
			array.put("msg", "����������");
			List<tb_problem> list = questiondao.getListbyType(questionlisttype);  
			array.put("data", list); 
			String str = JSONObject.toJSONString(array,SerializerFeature.WriteMapNullValue);  
			out.print(str);
			break;
		default:
			break;
		}
		 
	}
}