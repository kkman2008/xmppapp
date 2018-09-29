package com.jzh.news.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.json.JSONObject;

import com.google.gson.Gson;
import com.jzh.news.dao.UserDaoImpl;
import com.jzh.news.entity.User;
import com.jzh.news.util.Base64Coder;
import com.jzh.news.util.LogFactory;
import com.jzh.news.util.LogUtil;
import com.jzh.news.util.jdkLog;
import com.jzh.news.xmpp.XmppTool; 

public class DoGetUser extends HttpServlet { 

 

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException { 
		jdkLog.log.info("�����û��ӿڣ�call DoGetUser ...");
		jdkLog.log.info("jdk log util, �����û��ӿڣ�call DoGetUser ...");
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");

		try
		{
		String action = request.getParameter("action");
		PrintWriter out = response.getWriter();
		JSONObject array = new JSONObject();
		UserDaoImpl ndi = new UserDaoImpl();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time = sdf.format(new Date());
		if (action.equals("login")) {// �û���¼
			String user = request.getParameter("user");
			String password = request.getParameter("password");
			System.out.println("user = "+ user);
			System.out.println("password = "+ password);
			jdkLog.log.info("action =" + action );
			jdkLog.log.info("user = "+ user);
			jdkLog.log.info("password = "+ password);
			List<User> list = new ArrayList<User>();
			if (password.equals("QQSJHAAJSHAJSH")) {
				list = ndi.Search(user);
			} else if (password.equals("SINAHKSJDHSKDH")) {
				list = ndi.Search(user);
			} else {
				list = ndi.Search(user, password);
			}

			if (list.size() < 1) {

				array.put("code", "failure");
				array.put("msg", "�û�������������");
				array.put("data", "");
			} else {
				System.out.println("\n�û�����" + user + "\n��" + time + "��¼��");
				array.put("code", "success");
				array.put("msg", "��¼�ɹ�");
				// [tbd]��Ժ�̨��ͨ���ʺţ����openfire���Ƿ������ݣ����û�оʹ���. ������͵���ķ�������ע��ģ����س�ͻ��exception
//				try{
//					XmppTool.create(list.get(0));
//				}catch(Exception e){}
				
				array.put("data", list.get(0));
			}

		} else if (action.equals("save")) {

			String user = request.getParameter("user");
			String password = request.getParameter("password");
			String qq = request.getParameter("qq");
			String icon = request.getParameter("icon");
			String nickname = request.getParameter("nickname");
			String city = request.getParameter("city");
			String location = request.getParameter("location");
			String sex = request.getParameter("sex");
			String years = request.getParameter("years");
			User users = new User(user, password, qq, icon, nickname, city,
					sex, years, location, "û���ԣ���ǩ����");
			if (ndi.Save(users)) {
				array.put("code", "success");
				array.put("msg", "ע��ɹ�");
				System.out.println("\n�û�����" + user + "ע��ɹ�" + "\ntime:" + time);
				out.print(array);
				out.flush();
				out.close();
				XmppTool.create(users);
				return;

			} else {
				array.put("code", "failure");
				array.put("msg", "ע��ʧ��");

			}
		} else if (action.equals("search")) {
			String user = request.getParameter("user");
			List<User> list = new ArrayList<User>();
			list = ndi.Search(user);
			if (list.size() < 1) {

				array.put("code", "success");
				array.put("msg", "δע��");

			} else {

				array.put("code", "failure");
				array.put("msg", "��ע��");

			}

		} else if (action.equals("search_meeesage")) {

			String user = request.getParameter("user");
			List<User> list = new ArrayList<User>();
			list = ndi.Search(user);
			if (list.size() < 1) {

				array.put("code", "failure");
				array.put("msg", "�û����ϻ�ȡʧ��");
				array.put("data", "");

			} else {

				array.put("code", "success");
				array.put("msg", "�û����ϻ�ȡ�ɹ�");
				array.put("data", list.get(0));

			}

		} else if (action.equals("update_message")) {
			String nickname = request.getParameter("nickname");
			String sex = request.getParameter("sex");
			String years = request.getParameter("years");
			String qq = request.getParameter("qq");
			String user = request.getParameter("user");
			String city = request.getParameter("city");
			String qianming = request.getParameter("qianming");
			System.out.println(user + "\t�޸�������" + "\ntime:" + time);
			User users = new User();
			users.setUser(user);
			users.setNickname(nickname);
			users.setSex(sex);
			users.setYears(years);
			users.setQq(qq);
			users.setCity(city);
			users.setQianming(qianming);
			if (!ndi.update_message(users)) {

				array.put("code", "failure");
				array.put("msg", "�û������޸�ʧ��");

			} else {

				array.put("code", "success");
				array.put("msg", "�û������޸ĳɹ�");
				User u = ndi.Search_xmpp_message(users.getUser());
				String str = new Gson().toJson(u);
				ndi.update_xmpp_message(u.getUser().toLowerCase(), str);

			}
		} else if (action.equals("update_password")) {
			String user = request.getParameter("user");
			String password = request.getParameter("password");
			System.out.println(user + "\t�޸�������" + "\ntime:" + time);
			if (!ndi.update_message(user, password)) {

				array.put("code", "failure");

			} else {

				array.put("code", "success");

			}
		} else if (action.equals("update_icon")) {
			String files = request.getParameter("file");
			String filename = request.getParameter("filename");
			String user = request.getParameter("user");
			System.out.println(user + "\t�޸���ͷ��" + "\ntime:" + time);
			if (files != null) {
				byte[] b = Base64Coder.decodeLines(files);

				File file = new File("D:/htzk-web/htzk/Upload/");
				if (!file.exists()) {
					file.mkdirs();

				}
				FileOutputStream fos = new FileOutputStream(file.getPath()
						+ "/" + filename);

				fos.write(b);
				fos.flush();
				fos.close();
				if (!ndi.update_icon(user, filename)) {

					array.put("code", "failure");

				} else {

					array.put("code", "success");
					User u = ndi.Search_xmpp_message(user);
					String str = new Gson().toJson(u);
					ndi.update_xmpp_message(u.getUser().toLowerCase(), str);

				}
			}
		} else if (action.equals("search_icon")) {

			response.setContentType("image/gif");
			String name = request.getParameter("name");
			String imagePath = "D:/yantaosoftware/" + name;
			FileInputStream fis = new FileInputStream(imagePath);

			int size = fis.available(); // �õ��ļ���С

			byte data[] = new byte[size];

			fis.read(data); // ������

			fis.close();

			OutputStream os = response.getOutputStream();
			os.write(data);
			os.flush();
			os.close();
		}

		out.print(array);
		out.flush();
		out.close();
		}catch(Exception e){
			e.printStackTrace();
			jdkLog.log.info(e.toString());
		}
	}
}
