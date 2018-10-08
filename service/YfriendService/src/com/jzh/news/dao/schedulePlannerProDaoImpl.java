package com.jzh.news.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.jzh.news.entity.News_content;
import com.jzh.news.entity.seminarArragement;
import com.jzh.news.entity.tb_scheduleplannerpro; 
import com.jzh.news.util.ResultSetMapper;

public class schedulePlannerProDaoImpl  extends BaseDaoImpl {
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	Connection conn = null;

	public void CreateQuestion(tb_scheduleplannerpro model) {
		if (model.getCreatetime() == null) {
			model.setCreatetime(new Date());
		}
		conn = this.getYantaodbConnection();
		try {
			pstmt = conn.prepareStatement(ModelToSQL.getInsertSQL(model));
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.closeAll(null, pstmt, conn);
		}
	}

	public void UpdateQuestion(tb_scheduleplannerpro model) {
		conn = this.getYantaodbConnection();
		if (model.getCreatetime() == null) {
			model.setCreatetime(new Date());
		}
		try {
			pstmt = conn.prepareStatement(ModelToSQL.getUpdateSQL(model));
			pstmt.setObject(1, model.getTaskid());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.closeAll(null, pstmt, conn);
		}
	}
 

	public List<tb_scheduleplannerpro> getListbyTaskID(String TaskID) { 
		conn = this.getYantaodbConnection();
		try {
			String whereClausestr = ""; // "-1" is all status
			if (TaskID.equalsIgnoreCase("-1") == false) {
				whereClausestr = " where TaskID = '"
						+ TaskID.trim() + "'";
			}
			String sqlStatement = "select * from tb_scheduleplannerpro "
					+ whereClausestr;
			System.out.println(sqlStatement);
			pstmt = conn.prepareStatement(sqlStatement);
			rs = pstmt.executeQuery();
			ResultSetMapper<tb_scheduleplannerpro> resultSetMapper = new ResultSetMapper<tb_scheduleplannerpro>();
			List<tb_scheduleplannerpro> pojoList = resultSetMapper
					.mapRersultSetToObject(rs, tb_scheduleplannerpro.class);
			return pojoList;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.closeAll(rs, pstmt, conn);
		}
		return null;
	}
	
	public List<seminarArragement> getSeminarPlanbyTheme(String subjectid){
	 	conn = this.getYantaodbConnection();
	 	List<seminarArragement> seminarlst = new ArrayList<seminarArragement>();
		try {
			
			String sqlStatement = "SELECT tb_role.RoleId, tb_role.RoleName,tb_user.`Name`, tb_user.HeadImagePath "
						+ "	from tb_scheduleplannerpro "
						+ "		inner join tb_discussiongroup  "
						+ "		on tb_scheduleplannerpro.TaskID = tb_discussiongroup.TaskID "
						+ "		inner join tb_role "
						+ "		on tb_discussiongroup.RoleID = tb_role.RoleId "
						+ "		inner join tb_user "
						+ "		on tb_discussiongroup.UserID = tb_user.UserID "
						+ "		where tb_scheduleplannerpro.SubjectID = '" + subjectid + "'"; 
			System.out.println(sqlStatement);
			pstmt = conn.prepareStatement(sqlStatement);
			rs = pstmt.executeQuery();
			 
			// 获取会议编排信息，再更新其 研讨主题信息、编排信息、用户信息、角色信息
			while (rs.next()) {
				seminarArragement attendeeinfo = new seminarArragement();
				attendeeinfo.setRoleId(rs.getString("roleid"));
				attendeeinfo.setRoleName(rs.getString("rolename"));
				attendeeinfo.setUserName(rs.getString("name")); 
				attendeeinfo.setHeadImagePath(rs.getString("HeadImagePath")); 
				seminarlst.add(attendeeinfo);
			}
			return seminarlst;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.closeAll(rs, pstmt, conn);
		}
		return seminarlst;
	}
	
	public int getDicussCountbySubjectID(String subjectID){
		int dicusscount = 0 ;
		
		conn = this.getYantaodbConnection();
	 	List<seminarArragement> seminarlst = new ArrayList<seminarArragement>();
		try {
			String sqlStatement ="SELECT count(1) as dicusscount from tb_topicforumprocess " + 
					"where ThemeID =  '" + subjectID + "'";
			System.out.println(sqlStatement);
			pstmt = conn.prepareStatement(sqlStatement);
			rs = pstmt.executeQuery();
			 
			while (rs.next()) { 
				dicusscount = rs.getInt("dicusscount"); 
			}
			return dicusscount;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.closeAll(rs, pstmt, conn);
		}
		return dicusscount;
	}

	
	
}
