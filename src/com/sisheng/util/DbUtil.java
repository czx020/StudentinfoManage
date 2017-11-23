package com.sisheng.util;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class DbUtil {

	private static String dbUrl; // 数据库地址
	private static String dbUserName; // 账户名
	private static String dbPassword; // 账户密码
	private static String jdbcName; // jdbc名称

	/**
	 * 设置数据库连接参数
	 * 
	 * @throws IOException
	 */
	public static void setDbParameter() throws IOException {
		String propsFilename = "db.properties";    //properties文件名称
		Properties props = new Properties();         //properties类
		props.load(DbUtil.class.getClassLoader().getResourceAsStream(propsFilename));  //通过类加载器的路径来读取配置文件
		dbUrl = props.getProperty("dbUrl");
		dbUserName = props.getProperty("dbUserName");
		dbPassword = props.getProperty("dbPassword");
		jdbcName = props.getProperty("jdbcName");
	}

	/**
	 * 获取数据库连接
	 * 
	 * @return
	 * @throws Exception
	 * @throws Exception
	 */
	public static Connection getCon() throws Exception {

		setDbParameter();    //设置数据库连接参数
		Class.forName(jdbcName);    //注册驱动
		Connection con = DriverManager.getConnection(dbUrl, dbUserName,dbPassword);
		return con;
	}

	/**
	 * 关闭数据库连接
	 * 
	 * @param con
	 * @throws Exception
	 */
	public static void closeCon(Connection con) throws Exception {
		if (con != null) {
			con.close();
		}
	}

	// 测试
	public static void main(String[] args) {
		try {
			Connection conn = getCon();
			System.out.print("连接成功!");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
