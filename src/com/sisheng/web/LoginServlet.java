package com.sisheng.web;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sisheng.dao.UserDao;
import com.sisheng.model.User;
import com.sisheng.util.DbUtil;
import com.sisheng.util.StringUtil;

public class LoginServlet extends HttpServlet{
	
	DbUtil dbUtil = new DbUtil();    //���ݿ⹤����
	UserDao userUtil = new UserDao();   //�û����ݲ�����
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String userName=request.getParameter("userName");    //��ȡ�ύ��userName
		String password=request.getParameter("password");      //��ȡ�ύ��password
		request.setAttribute("userName", userName);                 //������������userName����
		request.setAttribute("password", password);                   //������������password����
		
		//�ж�Ϊ��
		if(StringUtil.isEmpty(userName)||StringUtil.isEmpty(password)){
			request.setAttribute("error", "�˺Ż�����Ϊ��!");          //������������error����,����ֵ
			request.getRequestDispatcher("index.jsp").forward(request, response); 
			return;
		}
		
		User user=new User(userName,password);
		Connection con=null;
		try {
			con=dbUtil.getCon();
			User currentUser=userUtil.login(con, user);  //��¼���
			if(currentUser==null){
				request.setAttribute("error", "�û������������");
				// ��������ת
				request.getRequestDispatcher("index.jsp").forward(request, response);
			}else{
				HttpSession session = request.getSession();
				// �ͻ�����ת
				response.sendRedirect("main.jsp");
				session.setAttribute("currentUser",currentUser);   //��session�������û�
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				dbUtil.closeCon(con);    //�ر����ݿ�����
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
