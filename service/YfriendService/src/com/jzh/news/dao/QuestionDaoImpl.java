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

/**
 * @author Jaylon
 * 
 */
public class QuestionDaoImpl extends BaseDaoImpl {
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	Connection conn = null;

	public void CreateQuestion(tb_problem model) {
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
		List<tb_problem> list = new ArrayList<>();
		conn = this.getYantaodbConnection();
		try {
			String whereClausestr = ""; // "0" is all status
			if (questionSatusType.equalsIgnoreCase("-1") == false) {
				whereClausestr = " where Phase = " + questionSatusType;
			}
			String sqlStatement = "select * from tb_problem " + whereClausestr;
			System.out.println(sqlStatement);
			pstmt = conn.prepareStatement(sqlStatement);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				tb_problem questionContent = new tb_problem();
				questionContent.setQuestionid(rs.getString("questionid"));
				questionContent.setProblemname(rs.getString("problemname"));
				questionContent.setProblemcontent(rs
						.getString("problemcontent"));
				questionContent.setAskthetime(rs.getDate("askthetime"));
				questionContent.setConclusion(rs.getString("conclusion"));
				list.add(questionContent);
			}

		} catch (SQLException e) {

			e.printStackTrace();
		} finally {
			this.closeAll(rs, pstmt, conn);

		}
		return list;
	}

	public void main() {
		tb_problem model = new tb_problem();
		model.setUserid("1");
		model.setQuestionid("7");
		model.setProblemname("航天气动问题");
		model.setIsdelete("0");
		model.setProblemcontent("航天气动问题正文");
		model.setPhase(0);
		CreateQuestion(model);
		model.setPhase(1);
		UpdateQuestion(model);
	}
}
