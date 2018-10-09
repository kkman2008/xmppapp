package com.jzh.news.entity;

import java.io.Serializable;

public class seminarArragement implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6143735321635337773L;
	String RoleId;
	String RoleName;
	String UserName;
	String HeadImagePath;
	public String getRoleId() {
		return RoleId;
	}
	public void setRoleId(String roleId) {
		RoleId = roleId;
	}
	public String getRoleName() {
		return RoleName;
	}
	public void setRoleName(String roleName) {
		RoleName = roleName;
	}
	public String getUserName() {
		return UserName;
	}
	public void setUserName(String userName) {
		UserName = userName;
	}
	public String getHeadImagePath() {
		return HeadImagePath;
	}
	public void setHeadImagePath(String headImagePath) {
		HeadImagePath = headImagePath;
	}
}
