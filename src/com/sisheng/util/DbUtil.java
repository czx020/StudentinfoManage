package com.sisheng.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DbUtil {
	
	private String dbUrl="jdbc:mysql://localhost:3306/db_studentInfo";  //数据库地址
	private String dbUserName="root";                //账户名
	private String dbPassword="a13229568334";  //账户密码
	private String jdbcName="com.mysql.jdbc.Driver";    //jdbc名称
	
	/**
	 * 获取数据库连接
	 * @return
	 * @throws Exception 
	 * @throws Exception
	 */
	public Connection getCon() throws Exception{
		Class.forName(jdbcName);
		Connection con = DriverManager.getConnection(dbUrl,dbUserName,dbPassword);
		return con;
	}
	
	/**
	 * 关闭数据库连接
	 * @param con
	 * @throws Exception
	 */
	public void closeCon(Connection con) throws Exception{
		if(con!=null){
			con.close();
		}
	}
		//测试
		public static void main(String[] args){
			DbUtil util = new DbUtil();
			try{
				util.getCon();
				System.out.print("数据库连接成功！");
			}catch(Exception e){
				e.printStackTrace();
			}
		}
}
