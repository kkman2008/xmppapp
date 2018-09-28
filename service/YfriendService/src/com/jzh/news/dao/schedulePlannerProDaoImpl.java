package com.jzh.news.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

}
