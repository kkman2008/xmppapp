package com.jzh.news.dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class BaseDaoImpl {

	/**
	 * 释放资源
	 * 
	 * @param rs
	 * @param pstmt
	 * @param conn
	 */
	public void closeAll(ResultSet rs, PreparedStatement pstmt, Connection conn) {
		try {
			if (rs != null) {
				rs.close();
			}
			if (pstmt != null) {
				pstmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static String url = null;

	private static String openFireUrl = null;
	private static String YantaodbUrl = null;
	
	private static String user = null;
	private static String password = null;
	

	private static String ytdbuser = null;
	private static String ytdbpassword = null;
	
	private static String driverClass = null;
	private static Properties prop = new Properties();

	static {
		Class clazz = BaseDaoImpl.class;
		InputStream in = clazz.getResourceAsStream("/jdbc.properties");
		try {
			prop.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
		url = prop.getProperty("jdbc.mysql.url");
		openFireUrl = prop.getProperty("jdbc.mysql.openFireUrl");
		YantaodbUrl = prop.getProperty("jdbc.mysql.zkUrl");
		user = prop.getProperty("jdbc.mysql.username");
		password = prop.getProperty("jdbc.mysql.password");
		ytdbuser = prop.getProperty("jdbc.mysql.zkusername");
		ytdbpassword = prop.getProperty("jdbc.mysql.zkpassword");
		driverClass = prop.getProperty("jdbc.mysql.driverClassName");
	}

	public Connection getConnection() {
		Connection conn = null;
		try {
			Class.forName(driverClass);
			conn = DriverManager.getConnection(url, user, password);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;

	}

	public Connection getOpenfireConnection() {
		Connection conn = null;
		try {
			Class.forName(driverClass);
			conn = DriverManager
					.getConnection(this.openFireUrl, ytdbuser, ytdbpassword);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}
	
	public Connection getYantaodbConnection(){
		Connection conn = null;
		try {
			System.out.println("YantaodbUrl =" + YantaodbUrl);
			System.out.println("ytdbuser =" + ytdbuser);
			System.out.println("ytdbpassword =" + ytdbpassword);
			Class.forName(driverClass);
			conn = DriverManager
					.getConnection(YantaodbUrl,  ytdbuser, ytdbpassword);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}
}
