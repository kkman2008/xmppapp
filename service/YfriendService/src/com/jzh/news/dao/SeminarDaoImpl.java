package com.jzh.news.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.jzh.news.entity.tb_theme;

public class SeminarDaoImpl extends BaseDaoImpl {

	PreparedStatement pstmt = null;
	ResultSet rs = null;
	Connection conn = null;
	
	public List<tb_theme> getListbyType(String questionSatusType){
		List<tb_theme> list = new ArrayList<>();
		conn = this.getYantaodbConnection();
		try {
			String whereClausestr = "where IsDelete = 0 "; // "0" is not deleted
			if( questionSatusType.equalsIgnoreCase("-1") == false){
				whereClausestr = whereClausestr + " and phase =" +  questionSatusType;
			}
			String sqlStatement = "select * from tb_theme " + whereClausestr;
			System.out.println(sqlStatement);
			pstmt = conn
					.prepareStatement(sqlStatement);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				tb_theme seminarContent = new tb_theme();
				seminarContent.setQuestionid( rs.getString("questionid"));
				seminarContent.setSubjectid(rs.getString("subjectid"));
				seminarContent.setQuestionname(rs.getString("questionname"));
				seminarContent.setQuestioncontent(rs.getString("questioncontent"));
				seminarContent.setDisintegratortime( rs.getDate( "disintegratortime")  ); 
				seminarContent.setConclusion( rs.getString("conclusion")); 
				seminarContent.setIsdelete(rs.getString("isdelete"));
				seminarContent.setPhase(rs.getInt("phase") );
				seminarContent.setManualorder(rs.getString("manualorder"));
				seminarContent.setSpeechmode(rs.getInt("speechmode"));
				list.add(seminarContent);
			}

		} catch (SQLException e) {

			e.printStackTrace();
		} finally {
			this.closeAll(rs, pstmt, conn);

		}
		return list; 
	}
	
	public void UpdateSeminarTopicPhase(String topicID, String phase, String speechMode){
		
		conn = this.getYantaodbConnection();
		try {
			pstmt = conn
					.prepareStatement("update tb_theme set phase=? , speechmode = ? where SubjectID='"
							+ topicID + "'");

			pstmt.setString(1, phase);
			pstmt.setString(2, speechMode);

			pstmt.executeUpdate();
			System.out.println("状态已修改成功, topicID = " +topicID + ", phase =" + phase + ", speechMode =" + speechMode); 
		} catch (SQLException e) { 
			e.printStackTrace(); 
		} finally {
			this.closeAll(null, pstmt, conn);
		}
		
	}

}
