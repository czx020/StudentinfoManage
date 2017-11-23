package com.sisheng.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.sisheng.model.Grade;
import com.sisheng.model.PageBean;
import com.sisheng.util.StringUtil;

public class GradeDao {

	/**
	 * ��ȡ�༶��Ϣ select * from t_grade
	 * 
	 * @param con
	 * @param pageBean
	 * @return
	 * @throws Exception
	 */
	public ResultSet gradeList(Connection con, PageBean pageBean, Grade grade)
			throws Exception {

		StringBuffer sb = new StringBuffer("select * from t_grade");

		// grade�İ༶����Ϊ��-->��������
		if (grade!=null&&!StringUtil.isEmpty(grade.getGradeName())) {
			sb.append(" and gradeName like '%" + grade.getGradeName() + "%'"); //
		}

		if (pageBean != null) {
			sb.append(" limit " + pageBean.getStart() + ","
					+ pageBean.getRows());
		}

		// ��������-->��and �滻��where������������Ȼ�����滻
		PreparedStatement pstmt = con.prepareStatement(sb.toString()
				.replaceFirst("and", "where"));

		return pstmt.executeQuery();
	}

	/**
	 * ��ȡ�༶��Ŀ
	 * 
	 * @param con
	 * @return
	 * @throws Exception
	 */
	public int gradeCount(Connection con, Grade grade) throws Exception {
		StringBuffer sb = new StringBuffer(
				"select count(*) as total from t_grade");
		if (!StringUtil.isEmpty(grade.getGradeName())) {
			sb.append(" and gradeName like '%" + grade.getGradeName() + "%'"); //
		}
		PreparedStatement pstmt = con.prepareStatement(sb.toString()
				.replaceFirst("and", "where"));
		ResultSet rs = pstmt.executeQuery();
		if (rs.next()) {
			return rs.getInt("total");
		} else {
			return 0;
		}
	}

	/**
	 * ɾ���༶ Ϊ�������û����飬Ҫ����ɾ���˶���������
	 * 
	 * @param con
	 * @param delIds
	 * @return
	 * @throws Exception
	 */
	public int gradeDelete(Connection con, String delIds) throws Exception {
		String sql = "delete from t_grade where id in(" + delIds + ")";// inЧ�ʸ�
		System.out.print(sql);
		PreparedStatement pstmt = con.prepareStatement(sql);
		return pstmt.executeUpdate();
	}

	/**
	 * ��Ӱ༶��Ϣ
	 * 
	 * @param con
	 * @param grade
	 * @return
	 * @throws Exception
	 */
	public int gradeAdd(Connection con, Grade grade) throws Exception {
		String sql = "insert t_grade values(null,?,?)";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setString(1, grade.getGradeName());
		pstmt.setString(2, grade.getGradeDesc());
		return pstmt.executeUpdate();
	}

	/**
	 * �޸İ༶��Ϣ
	 * @param con
	 * @param grade
	 * @return
	 * @throws Exception
	 */
	public int gradeModify(Connection con, Grade grade) throws Exception {
		String sql = "update t_grade set gradeName=?,gradeDesc=? where id=?";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setString(1, grade.getGradeName());
		pstmt.setString(2, grade.getGradeDesc());
		pstmt.setInt(3, grade.getId());
		return pstmt.executeUpdate();
	}
}
