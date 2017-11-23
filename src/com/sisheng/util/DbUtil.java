package com.sisheng.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DbUtil {
	
	private String dbUrl="jdbc:mysql://localhost:3306/db_studentInfo";  //���ݿ��ַ
	private String dbUserName="root";                //�˻���
	private String dbPassword="a13229568334";  //�˻�����
	private String jdbcName="com.mysql.jdbc.Driver";    //jdbc����
	
	/**
	 * ��ȡ���ݿ�����
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
	 * �ر����ݿ�����
	 * @param con
	 * @throws Exception
	 */
	public void closeCon(Connection con) throws Exception{
		if(con!=null){
			con.close();
		}
	}
		//����
		public static void main(String[] args){
			DbUtil util = new DbUtil();
			try{
				util.getCon();
				System.out.print("���ݿ����ӳɹ���");
			}catch(Exception e){
				e.printStackTrace();
			}
		}
}
