/**
 * 
 */
package com.jzh.news.dao;

import java.util.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import com.jzh.news.entity.tb_problem;
import com.jzh.news.entity.tb_topicprocessuserpraise;
import com.jzh.news.util.ResultSetMapper;

/**
 * @author Jaylon
 * 
 */
public class QuestionDaoImpl extends BaseDaoImpl {
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	Connection conn = null;

	public void CreateQuestion(tb_problem model) {
		model.setIsdelete("0");
		if (model.getAskthetime() == null) {
			model.setAskthetime(new Date());
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

	public void UpdateQuestion(tb_problem model) {
		conn = this.getYantaodbConnection();
		if (model.getAskthetime() == null) {
			model.setAskthetime(new Date());
		}
		try {
			pstmt = conn.prepareStatement(ModelToSQL.getUpdateSQL(model));
			pstmt.setObject(1, model.getQuestionid());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.closeAll(null, pstmt, conn);
		}
	}

	public List<tb_problem> getListbyType(String questionSatusType) {
		List<tb_problem> pojoList = new ArrayList<>();
		conn = this.getYantaodbConnection();
		try {
			String whereClausestr = ""; // "0" is all status
			if (questionSatusType.equalsIgnoreCase("-1") == false) {
				whereClausestr = " and Phase = " + questionSatusType;
			}
			String sqlStatement = "select * from tb_problem where isdelete = 0 "
					+ whereClausestr;
			System.out.println(sqlStatement);
			pstmt = conn.prepareStatement(sqlStatement);
			rs = pstmt.executeQuery();
			ResultSetMapper<tb_problem> resultSetMapper = new ResultSetMapper<tb_problem>();
			pojoList = resultSetMapper.mapRersultSetToObject(rs,
					tb_problem.class);

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.closeAll(rs, pstmt, conn);

		}
		return pojoList;
	}

	public static void main(String[] args) {
		// tb_problem model = new tb_problem();
		// model.setUserid("1");
		// model.setQuestionid("7");
		// model.setProblemname("������������");
		// model.setIsdelete("0");
		// model.setProblemcontent("����������������");
		// model.setPhase(0);
		// CreateQuestion(model);
		// model.setPhase(1);
		// UpdateQuestion(model);
		QuestionDaoImpl qdi = new QuestionDaoImpl();
		List<tb_problem> plist = qdi.getListbyType("-1");
		for (tb_problem tp : plist) {
			System.out.println(tp.getQuestionid());
			System.out.println(tp.getProblemname());
		}
	}
}
