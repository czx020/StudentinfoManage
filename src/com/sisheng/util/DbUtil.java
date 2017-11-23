package com.sisheng.util;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class DbUtil {

	private static String dbUrl; // ���ݿ��ַ
	private static String dbUserName; // �˻���
	private static String dbPassword; // �˻�����
	private static String jdbcName; // jdbc����

	/**
	 * �������ݿ����Ӳ���
	 * 
	 * @throws IOException
	 */
	public static void setDbParameter() throws IOException {
		String propsFilename = "db.properties";    //properties�ļ�����
		Properties props = new Properties();         //properties��
		props.load(DbUtil.class.getClassLoader().getResourceAsStream(propsFilename));  //ͨ�����������·������ȡ�����ļ�
		dbUrl = props.getProperty("dbUrl");
		dbUserName = props.getProperty("dbUserName");
		dbPassword = props.getProperty("dbPassword");
		jdbcName = props.getProperty("jdbcName");
	}

	/**
	 * ��ȡ���ݿ�����
	 * 
	 * @return
	 * @throws Exception
	 * @throws Exception
	 */
	public static Connection getCon() throws Exception {

		setDbParameter();    //�������ݿ����Ӳ���
		Class.forName(jdbcName);    //ע������
		Connection con = DriverManager.getConnection(dbUrl, dbUserName,dbPassword);
		return con;
	}

	/**
	 * �ر����ݿ�����
	 * 
	 * @param con
	 * @throws Exception
	 */
	public static void closeCon(Connection con) throws Exception {
		if (con != null) {
			con.close();
		}
	}

	// ����
	public static void main(String[] args) {
		try {
			Connection conn = getCon();
			System.out.print("���ӳɹ�!");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
