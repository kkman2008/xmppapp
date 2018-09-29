package com.jzh.news.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.jzh.news.entity.News_content;
import com.jzh.news.entity.News_pinglun;
import com.jzh.news.entity.User;
import com.jzh.news.entity.tb_topicprocessuserpraise;
import com.jzh.news.entity.tb_user;
import com.jzh.news.util.ResultSetMapper;
import com.jzh.news.util.jdkLog;

public class UserDaoImpl extends BaseDaoImpl {
	List<User> list = new ArrayList<User>();
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	Connection conn = null;

	/**
	 * ��ѯ����ע���û�
	 * 
	 * @return
	 */
	public List<User> Search() {
		conn = this.getYantaodbConnection();
		try {

			pstmt = conn.prepareStatement("select * from tb_user");
			rs = pstmt.executeQuery();
			while (rs.next()) {
				User content = new User();
				content.setId(rs.getString("UserID"));
				content.setUser(rs.getString("Account"));
				content.setPassword(rs.getString("password"));
				//content.setQq(rs.getString("qq"));
				content.setIcon(rs.getString("HeadImagePath"));
				content.setNickname(rs.getString("name"));
				//content.setCity(rs.getString("city"));
				content.setSex(rs.getString("sex"));
				//content.setYears(rs.getString("years"));
				content.setQianming(rs.getString("qianming"));
				list.add(content);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this.closeAll(rs, pstmt, conn);

		}
		return list;
	}

	/**
	 * �����û�����ѯע���û�
	 * 
	 * @return
	 */
	public User Search_xmpp_message(String user) {
		conn = this.getYantaodbConnection();
		User content = null;
		try {

			pstmt = conn.prepareStatement("select * from tb_user where user='"
					+ user + "'");
			rs = pstmt.executeQuery();
			while (rs.next()) {
				content = new User();
				content.setId(rs.getString("UserID"));
				content.setUser(rs.getString("Account"));
				//content.setQq(rs.getString("qq"));
				content.setIcon(rs.getString("HeadImagePath"));
				content.setNickname(rs.getString("name"));
				//content.setCity(rs.getString("city"));
				content.setSex(rs.getString("sex"));
				//content.setYears(rs.getString("years"));
				content.setQianming(rs.getString("qianming"));
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this.closeAll(rs, pstmt, conn);

		}
		return content;
	}

	/**
	 * �����û�����ѯ�û�
	 * 
	 * @return
	 */
	public User Search_one(String user) {
		conn = this.getYantaodbConnection();
		User content = null;
		try {

			pstmt = conn.prepareStatement("select * from tb_user where user='"
					+ user + "'");
			rs = pstmt.executeQuery();
			while (rs.next()) {
				content = new User();
				content.setIcon(rs.getString("HeadImagePath"));
				content.setNickname(rs.getString("name"));
				content.setSex(rs.getString("sex"));

			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this.closeAll(rs, pstmt, conn);

		}
		return content;

	}

	/**
	 * �����˺��ж��Ƿ��Ѿ�ע��ͻ�ȡ�û�����
	 * 
	 * @return
	 */
	public List<User> Search(String user) {
		conn = this.getYantaodbConnection();
		User content = null;
		try {
// the account as the login id
			String sqlstr = "select * from tb_user where Account='"
					+ user + "'";
			System.out.println("sqlstr = " + sqlstr);
			pstmt = conn.prepareStatement(sqlstr);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				content = new User();
				content.setId(rs.getString("UserID"));
				content.setUser(rs.getString("Account"));
				content.setPassword(rs.getString("password"));
				content.setQq(rs.getString("qq"));
				content.setIcon(rs.getString("HeadImagePath"));
				content.setNickname(rs.getString("name"));
				content.setCity(rs.getString("city"));
				content.setSex(rs.getString("sex"));
				content.setYears(rs.getString("years"));
				content.setLocation(rs.getString("location"));
				content.setQianming(rs.getString("qianming"));
				list.add(content);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this.closeAll(rs, pstmt, conn);

		}
		return list;
	}

	/**
	 * �����û����������ж��Ƿ��¼�ɹ�
	 * 
	 * @return
	 */
	public List<User> Search(String user, String password) {
		conn = this.getYantaodbConnection();
		User content = null;
		try {

			pstmt = conn.prepareStatement("select * from tb_user where Account='"
					+ user + "'and password='" + password + "'");
			rs = pstmt.executeQuery();
			while (rs.next()) {
				content = new User();
				content.setId(rs.getString("UserID"));
				content.setUser(rs.getString("Account"));
				content.setPassword(rs.getString("password"));
				content.setQq(rs.getString("qq"));
				content.setIcon(rs.getString("HeadImagePath"));
				content.setNickname(rs.getString("name"));
				content.setCity(rs.getString("city"));
				content.setLocation(rs.getString("location"));
				content.setSex(rs.getString("sex"));
				content.setYears(rs.getString("years"));
				content.setQianming(rs.getString("qianming"));
				list.add(content);

			}

		} catch (SQLException e) { 
			e.printStackTrace();
		} finally {
			this.closeAll(rs, pstmt, conn);
		}
		return list;

	}

	/**
	 * �������û�
	 * 
	 * @param news
	 */
	public boolean Save(User user) {
		conn = this.getYantaodbConnection();
		try {
			pstmt = conn
					.prepareStatement("insert into tb_user(UserID, Account,password,qq,HeadImagePath,name,city,sex,years,location,qianming)values(?,?,?,?,?,?,?,?,?,?,?)");
			pstmt.setString(1, UUID.randomUUID().toString());
			pstmt.setString(2, user.getUser());
			pstmt.setString(3, user.getPassword());
			pstmt.setString(4, user.getQq());
			pstmt.setString(5, user.getIcon());
			pstmt.setString(6, user.getNickname());
			pstmt.setString(7, user.getCity());
			pstmt.setString(8, user.getSex());
			pstmt.setString(9, user.getYears());
			pstmt.setString(10, user.getLocation());
			pstmt.setString(11, user.getQianming());
			pstmt.executeUpdate();
			return true;
		} catch (SQLException e) {
			jdkLog.log.info(e.toString());
			e.printStackTrace();
			return false;
		} finally {
			this.closeAll(null, pstmt, conn);
		}
	}

	/**
	 * �޸��û�����
	 * 
	 * @param news
	 */
	public boolean update_message(User user) {
		conn = this.getYantaodbConnection();
		try {
			pstmt = conn
					.prepareStatement("update tb_user set qq=?,name=?,city=?,sex=?,years=?,qianming=? where Account='"
							+ user.getUser() + "'");

			pstmt.setString(1, user.getQq());
			pstmt.setString(2, user.getNickname());
			pstmt.setString(3, user.getCity());
			pstmt.setString(4, user.getSex());
			pstmt.setString(5, user.getYears());
			pstmt.setString(6, user.getQianming());
			pstmt.executeUpdate();
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} finally {
			this.closeAll(null, pstmt, conn);
		}
	}

	/**
	 * �޸��û�����
	 * 
	 * @param news
	 */
	public boolean update_qianming(String user) {
		conn = this.getYantaodbConnection();
		try {
			pstmt = conn
					.prepareStatement("update tb_user set location=? where Account='"
							+ user + "'");

			pstmt.setString(1, "δ֪����");

			pstmt.executeUpdate();
			System.out.println(user + "δ֪�����޸ĳɹ�");
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} finally {
			this.closeAll(null, pstmt, conn);
		}
	}

	/**
	 * �޸�xmpp�û�����
	 * 
	 * @param news
	 */
	public boolean update_xmpp_message(String user, String name) {
		conn = this.getOpenfireConnection();
		try {
			pstmt = conn
					.prepareStatement("update ofuser set NAME=? where username='"
							+ user + "'");

			pstmt.setString(1, name);

			pstmt.executeUpdate();
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} finally {
			this.closeAll(null, pstmt, conn);
		}
	}

	/**
	 * �޸�����
	 * 
	 * @param news
	 */
	public boolean update_message(String user, String password) {
		conn = this.getYantaodbConnection();
		try {
			pstmt = conn
					.prepareStatement("update tb_user set password=? where Account='"
							+ user + "'");

			pstmt.setString(1, password);

			pstmt.executeUpdate();
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} finally {
			this.closeAll(null, pstmt, conn);
		}
	}

	/**
	 * �޸�ͷ��
	 * 
	 * @param news
	 */
	public boolean update_icon(String user, String HeadImagePath) {
		conn = this.getYantaodbConnection();
		try {
			pstmt = conn
					.prepareStatement("update tb_user set HeadImagePath=? where Account='"
							+ user + "'");

			pstmt.setString(1, HeadImagePath);

			pstmt.executeUpdate();
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} finally {
			this.closeAll(null, pstmt, conn);
		}
	}
	
	/**
	 * �����˺��ж��Ƿ��Ѿ�ע��ͻ�ȡ�û�����
	 * 
	 * @return
	 */
	public List<tb_user> SearchPro(String user) {
		conn = this.getYantaodbConnection();
		User content = null;
		try { 
			String sqlstr = "select * from tb_user where Account='"
					+ user + "'";
			System.out.println("sqlstr = " + sqlstr);
			pstmt = conn.prepareStatement(sqlstr);
			rs = pstmt.executeQuery();
			ResultSetMapper<tb_user> resultSetMapper = new ResultSetMapper<tb_user>();
			List<tb_user> pojoList = resultSetMapper
					.mapRersultSetToObject(rs, tb_user.class);	
			return pojoList;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this.closeAll(rs, pstmt, conn);

		}
		return null;
	}
	
	/**
	 * �����û����������ж��Ƿ��¼�ɹ�
	 * 
	 * @return
	 */
	public List<tb_user> SearchPro(String user, String password) {
		conn = this.getYantaodbConnection();
		User content = null;
		try {
			String sqlstr = "select * from tb_user where Account='"
					+ user + "'and password='" + password + "'";
			System.out.println("sqlstr =" + sqlstr );
			pstmt = conn.prepareStatement(sqlstr);
			rs = pstmt.executeQuery(); 
			ResultSetMapper<tb_user> resultSetMapper = new ResultSetMapper<tb_user>();
			List<tb_user> pojoList = resultSetMapper
					.mapRersultSetToObject(rs, tb_user.class);	
			return pojoList;
		} catch (SQLException e) { 
			e.printStackTrace();
		} finally {
			this.closeAll(rs, pstmt, conn);
		}
		return null;
	}
	/**
	 * �������û�
	 * 
	 * @param user
	 */
	public boolean SavePro(tb_user user) {
		if (user.getCreatetime()  == null) {
			user.setCreatetime(new Date());
		}
		conn = this.getYantaodbConnection();
		try {
			pstmt = conn.prepareStatement(ModelToSQL.getInsertSQL(user));
			pstmt.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			this.closeAll(null, pstmt, conn);
		} 
	}
	

	/**
	 * �޸��û�����
	 * 
	 * @param news
	 */
	public boolean update_message_pro(tb_user user) {
		conn = this.getYantaodbConnection();
		try {
			String sql = "update tb_user set qq=?,name=?,city=?,sex=?,years=?,qianming=? where Account='"
					+ user.getAccount() + "'";
			pstmt = conn
					.prepareStatement(sql);
			System.out.println("sql = " + sql);

			pstmt.setString(1, user.getQq());
			pstmt.setString(2, user.getName());
			pstmt.setString(3, user.getCity());
			pstmt.setString(4, user.getSex());
			pstmt.setString(5, user.getYears());
			pstmt.setString(6, user.getQianming());
			pstmt.executeUpdate();
			return true;
		} catch (SQLException e) { 
			e.printStackTrace();
			return false;
		} finally {
			this.closeAll(null, pstmt, conn);
		}
	}
	
	/**
	 * �����û�����ѯע���û�
	 * 
	 * @return
	 */
	public tb_user Search_xmpp_message_pro(String user) {
		conn = this.getYantaodbConnection();
		tb_user content = null;
		try {

			pstmt = conn.prepareStatement("select * from tb_user where Account='"
					+ user + "'");
			rs = pstmt.executeQuery();
			while (rs.next()) {
				content = new tb_user();
				content.setUserid(rs.getString("UserID"));
				content.setAccount(rs.getString("Account")); 
				content.setHeadimagepath(rs.getString("HeadImagePath"));
				content.setName(rs.getString("name")); 
				content.setSex(rs.getString("sex")); 
				content.setQianming(rs.getString("qianming"));
			}

		} catch (SQLException e) { 
			e.printStackTrace();
		} finally {
			this.closeAll(rs, pstmt, conn);
		}
		return content;
	}
	
	/**
	 * �޸�xmpp�û�����
	 * 
	 * @param news
	 */
	public boolean update_xmpp_message_pro(String user, String name) {
		conn = this.getOpenfireConnection();
		try {
			pstmt = conn
					.prepareStatement("update ofuser set NAME=? where username='"
							+ user + "'");
			pstmt.setString(1, name);
			pstmt.executeUpdate();
			return true;
		} catch (SQLException e) { 
			e.printStackTrace();
			return false;
		} finally {
			this.closeAll(null, pstmt, conn);
		}
	}

}
