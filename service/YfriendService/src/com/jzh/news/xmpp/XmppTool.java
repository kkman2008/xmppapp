package com.jzh.news.xmpp;

import java.util.HashMap;
import java.util.Map;

import org.jivesoftware.smack.AccountManager;
import org.jivesoftware.smack.Connection;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;

import com.google.gson.Gson;
import com.jzh.news.entity.User;
import com.jzh.news.entity.tb_user;
import com.jzh.news.util.jdkLog;

/**
 * <b>function:</b> ����Smack������ XMPP Э��ͨ��
 * 
 * @author hoojo
 * @createDate 2012-5-22 ����10:28:18
 * @file ConnectionServerTest.java
 * @package com.hoo.smack.conn
 * @project jwchat
 * @blog http://blog.csdn.net/IBM_hoojo
 * @email hoojo_@126.com
 * @version 1.0
 */
public class XmppTool {

	private static Connection connection;
	private static ConnectionConfiguration config;

	// public static String server = "123.207.145.194";
	public static String server = "127.0.0.1";

	public static boolean create(User user) {
		if (connection == null) {
			init(false);
		} else {
			connection.disconnect();
			init(true);
		}

		// String strs = user.getUser() + ";" + user.getNickname() + ";"
		// + user.getIcon() + ";" + user.getSex();
		String pswd = user.getPassword();
		user.setPassword(null);
		User userAttributeFilter = new User();
		userAttributeFilter.setUser(user.getUser());
		userAttributeFilter.setPassword(user.getPassword());
		String strs = new Gson().toJson(userAttributeFilter);
		System.out.println(userAttributeFilter);
		AccountManager accountManager = connection.getAccountManager();
		try {
			/**
			 * ����һ���û�boy������Ϊboy��������ڹ���Ա����̨ҳ��http://192.168.8.32:9090/user-
			 * summary.jsp�鿴�û�/��������Ϣ�����鿴�Ƿ�ɹ������û�
			 */

			Map<String, String> map = new HashMap<String, String>();
			map.put("name", strs);
			map.put("email", "113531420@qq.com");
			accountManager.createAccount(userAttributeFilter.getUser(), pswd,
					map);
			System.out.println(user.getUser() + "\t" + pswd + "��xmppע��ɹ�");
			return true;
			/** �޸����� */
			// accountManager.changePassword("abc");
		} catch (XMPPException e) {
			jdkLog.log.info(e.toString());
			e.printStackTrace();
			return true;
		}
	}
	
	public static boolean createpro(tb_user user) {
		if (connection == null) {
			init(false);
		} else {
			connection.disconnect();
			init(true);
		}
		String pswd = user.getPassword();
		user.setPassword(null);
		User userAttributeFilter = new User();
		userAttributeFilter.setUser(user.getAccount());
		userAttributeFilter.setPassword(user.getPassword());
		String strs = new Gson().toJson(userAttributeFilter);
		System.out.println(userAttributeFilter);
		AccountManager accountManager = connection.getAccountManager();
		try { 

			Map<String, String> map = new HashMap<String, String>();
			map.put("name", strs);
			map.put("email", "113531420@qq.com");
			accountManager.createAccount(userAttributeFilter.getUser(), pswd,
					map);
			System.out.println(user.getAccount() + "\t" + pswd + "��xmppע��ɹ�");
			return true; 
		} catch (XMPPException e) {
			jdkLog.log.info(e.toString());
			e.printStackTrace();
			return true;
		}
	}

	/**
	 * ��¼
	 * 
	 * @param user
	 * @param password
	 */
	public static void login(String user, String password) {

		if (connection == null) {
			init(true);
		} else {
			connection.disconnect(); 
			connection = null;
			config = null; 
			// �������ӣ�����SASLAuthentication��
			init(true);
		}
		try {
			/** �û���½���û��������� */
			connection.login(user, password);
		} catch (XMPPException e) {
			e.printStackTrace();
		}
		/** ��ȡ��ǰ��½�û� */
		fail("User:", connection.getUser());
		addGroup(connection.getRoster(), "�ҵĺ���");
		addGroup(connection.getRoster(), "������");
		System.out.println("OK");

	}

	/** * ���һ���� */
	public static boolean addGroup(Roster roster, String groupName) {
		try {
			roster.createGroup(groupName);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	// -----------���²��ÿ�-----------------------------------------------------

	private final static void fail(Object o) {
		if (o != null) {
			System.out.println(o);
		}
	}

	private final static void fail(Object o, Object... args) {
		if (o != null && args != null && args.length > 0) {
			String s = o.toString();
			for (int i = 0; i < args.length; i++) {
				String item = args[i] == null ? "" : args[i].toString();
				if (s.contains("{" + i + "}")) {
					s = s.replace("{" + i + "}", item);
				} else {
					s += " " + item;
				}
			}
			System.out.println(s);
		}
	}

	/**
	 * <b>function:</b> ��ʼSmack��openfire���������ӵĻ�������
	 * 
	 * @author hoojo
	 * @createDate 2012-6-25 ����04:06:42
	 */

	public static void init(Boolean bSASLAuthentication) {
		try {
			// connection = new XMPPConnection(server);
			// connection.connect();

			/**
			 * 5222��openfire������Ĭ�ϵ�ͨ�Ŷ˿ڣ�����Ե�¼http://192.168.8.32:9090/
			 * ������Ա����̨�鿴�ͻ��˵��������˿�
			 */
			config = new ConnectionConfiguration(server, 5222);

			/** �Ƿ�����ѹ�� */
			config.setCompressionEnabled(true);
			/** �Ƿ����ð�ȫ��֤ */
			config.setSASLAuthenticationEnabled(bSASLAuthentication);
			/** �Ƿ����õ��� */
			config.setDebuggerEnabled(false);
			// config.setReconnectionAllowed(true);
			// config.setRosterLoadedAtLogin(true);

			/** ����connection���� */
			connection = new XMPPConnection(config);
			/** �������� */
			connection.connect();
		} catch (XMPPException e) {
			e.printStackTrace();
		}
		// fail(connection);
		// fail(connection.getConnectionID());
	}

}