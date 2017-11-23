package com.sisheng.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.sisheng.model.User;

public class UserDao {

	/**
	 * µÇÂ¼ÑéÖ¤
	 * 
	 * @author sisheng
	 * @return User
	 * @param con
	 * @param user
	 * @throws Exception
	 * @throws Exception
	 */
	public User login(Connection con, User user) throws Exception {
		User result = null;
		String sql = "select * from t_user where userName=? and password=?";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setString(1, user.getUsername());
		pstmt.setString(2, user.getPassword());
		ResultSet rs = pstmt.executeQuery();
		if (rs.next()) {
			result = new User();
			result.setUsername(user.getUsername());
			result.setPassword(user.getPassword());
		}
		return result;
	}
}
