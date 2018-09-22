package com.jzh.news.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.jzh.news.entity.tb_topicprocessuserpraise;
import com.jzh.news.util.ResultSetMapper;




public class SeminarDicussCommentDaoImpl extends BaseDaoImpl {

	PreparedStatement pstmt = null;
	ResultSet rs = null;
	Connection conn = null;

	public void CreateDicussComment(tb_topicprocessuserpraise model) {
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

	public void UpdateDicussComment(tb_topicprocessuserpraise model) {
		conn = this.getYantaodbConnection();
		if (model.getCreatetime() == null) {
			model.setCreatetime(new Date());
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

	// 某个研讨主题讨论的表态与点赞

	public List<tb_topicprocessuserpraise> getListbyTopicProcessID(String topicprocessid) {
		List<tb_topicprocessuserpraise> list = new ArrayList<>();
		conn = this.getYantaodbConnection();
		try {
			String whereClausestr = "";  
			whereClausestr = " where topicprocessid = '" + topicprocessid.trim() + "'";
			String sqlStatement = "select * from tb_topicprocessuserpraise "
					+ whereClausestr;
			System.out.println(sqlStatement);
			pstmt = conn.prepareStatement(sqlStatement);
			rs = pstmt.executeQuery();
			ResultSetMapper<tb_topicprocessuserpraise> resultSetMapper = new ResultSetMapper<tb_topicprocessuserpraise>();
			List<tb_topicprocessuserpraise> pojoList = resultSetMapper
					.mapRersultSetToObject(rs, tb_topicprocessuserpraise.class);
			return pojoList;

		} catch (SQLException e) {

			e.printStackTrace();
		} finally {
			this.closeAll(rs, pstmt, conn);

		}
		return list;
	}

	public static void main(String[] args) {
		tb_topicprocessuserpraise model = new tb_topicprocessuserpraise();
		model.setId(UUID.randomUUID().toString() );
		model.setTopicprocessid("f346f402-7b3b-4791-9211-10ec24047708");
		model.setUserid("1"); 
		model.setComment("当前评论");
		model.setUsername("黄教授");
		model.setUserid("98");
		// jp类里面的boolean数据类型丢失了， 需要做一些update
		model.setIspraise(true);
		SeminarDicussCommentDaoImpl daoimpl = new SeminarDicussCommentDaoImpl();
		daoimpl.CreateDicussComment(model);
		model.setIsfavorite(true);
		daoimpl.UpdateDicussComment(model);
		List<tb_topicprocessuserpraise> pojoList = daoimpl.getListbyTopicProcessID("f346f402-7b3b-4791-9211-10ec24047708");
		for (tb_topicprocessuserpraise entity : pojoList) {
			System.out.println(entity.getId());
			System.out.println(entity.getComment());
		}
	}

}
