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
	 * 获取班级信息 select * from t_grade
	 * 
	 * @param con
	 * @param pageBean
	 * @return
	 * @throws Exception
	 */
	public ResultSet gradeList(Connection con, PageBean pageBean, Grade grade)
			throws Exception {

		StringBuffer sb = new StringBuffer("select * from t_grade");

		// grade的班级名不为空-->搜索操作
		if (grade!=null&&!StringUtil.isEmpty(grade.getGradeName())) {
			sb.append(" and gradeName like '%" + grade.getGradeName() + "%'"); //
		}

		if (pageBean != null) {
			sb.append(" limit " + pageBean.getStart() + ","
					+ pageBean.getRows());
		}

		// 搜索操作-->把and 替换成where，不是搜索自然不会替换
		PreparedStatement pstmt = con.prepareStatement(sb.toString()
				.replaceFirst("and", "where"));

		return pstmt.executeQuery();
	}

	/**
	 * 获取班级数目
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
	 * 删除班级 为了提升用户体验，要返回删除了多少条数据
	 * 
	 * @param con
	 * @param delIds
	 * @return
	 * @throws Exception
	 */
	public int gradeDelete(Connection con, String delIds) throws Exception {
		String sql = "delete from t_grade where id in(" + delIds + ")";// in效率高
		System.out.print(sql);
		PreparedStatement pstmt = con.prepareStatement(sql);
		return pstmt.executeUpdate();
	}

	/**
	 * 添加班级信息
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
	 * 修改班级信息
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
