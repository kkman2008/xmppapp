/**
 * 
 */
package com.jzh.news.dao;

import java.util.*;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import com.jzh.news.entity.tb_topicforumprocess;
import com.jzh.news.util.ResultSetMapper;

/**
 * @author Jaylon
 * 
 */
public class SeminarDiscussProccessDaoImpl extends BaseDaoImpl {
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	Connection conn = null;

	public void CreateQuestion(tb_topicforumprocess model) {
		if (model.getDiscusstime() == null) {
			model.setDiscusstime(new Date());
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

	public void UpdateQuestion(tb_topicforumprocess model) {
		conn = this.getYantaodbConnection();
		if (model.getDiscusstime() == null) {
			model.setDiscusstime(new Date());
		}
		try {
			pstmt = conn.prepareStatement(ModelToSQL.getUpdateSQL(model));
			pstmt.setObject(1, model.getId());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.closeAll(null, pstmt, conn);
		}
	}

	// 1.主要观点；2.重点问题；3.指标数据；4.结论

	public List<tb_topicforumprocess> getListbyType(String discussType) {
		List<tb_topicforumprocess> list = new ArrayList<>();
		conn = this.getYantaodbConnection();
		try {
			String whereClausestr = ""; // "0" is all status
			if (discussType.equalsIgnoreCase("-1") == false) {
				whereClausestr = " where ThemeID = '"
						+ discussType.trim() + "'";
			}
			String sqlStatement = "select * from tb_topicforumprocess "
					+ whereClausestr + " order by DiscussTime desc" ;
			System.out.println(sqlStatement);
			pstmt = conn.prepareStatement(sqlStatement);
			rs = pstmt.executeQuery();
			ResultSetMapper<tb_topicforumprocess> resultSetMapper = new ResultSetMapper<tb_topicforumprocess>();
			List<tb_topicforumprocess> pojoList = resultSetMapper
					.mapRersultSetToObject(rs, tb_topicforumprocess.class);
			return pojoList;

		} catch (SQLException e) {

			e.printStackTrace();
		} finally {
			this.closeAll(rs, pstmt, conn);

		}
		return list;
	}

	public static void main(String[] args) {
		tb_topicforumprocess model = new tb_topicforumprocess();
		model.setId(UUID.randomUUID().toString());
		model.setUserid("1");
		model.setThemeid("7");
		model.setDiscusscontenttype("1");
		model.setDiscusscontent("航天气动问题正文");
		SeminarDiscussProccessDaoImpl daoimpl = new SeminarDiscussProccessDaoImpl();
		daoimpl.CreateQuestion(model);
		model.setDiscusscontenttype("2");
		daoimpl.UpdateQuestion(model);
		List<tb_topicforumprocess> pojoList = daoimpl.getListbyType("327e2fb7-2c3c-4d64-bbc5-0f00ae80d2bd");
		for (tb_topicforumprocess entity : pojoList) {
			System.out.println(entity.getId());
			System.out.println(entity.getDiscusscontent());
		}
	}
}
