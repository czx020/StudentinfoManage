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
	
	DbUtil dbUtil = new DbUtil();    //数据库工具类
	UserDao userUtil = new UserDao();   //用户数据操作类
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String userName=request.getParameter("userName");    //获取提交的userName
		String password=request.getParameter("password");      //获取提交的password
		request.setAttribute("userName", userName);                 //在请求中设置userName属性
		request.setAttribute("password", password);                   //在请求中设置password属性
		
		//判断为空
		if(StringUtil.isEmpty(userName)||StringUtil.isEmpty(password)){
			request.setAttribute("error", "账号或密码为空!");          //在请求中设置error属性,并赋值
			request.getRequestDispatcher("index.jsp").forward(request, response); 
			return;
		}
		
		User user=new User(userName,password);
		Connection con=null;
		try {
			con=dbUtil.getCon();
			User currentUser=userUtil.login(con, user);  //登录检查
			if(currentUser==null){
				request.setAttribute("error", "用户名或密码错误！");
				// 服务器跳转
				request.getRequestDispatcher("index.jsp").forward(request, response);
			}else{
				HttpSession session = request.getSession();
				// 客户端跳转
				response.sendRedirect("main.jsp");
				session.setAttribute("currentUser",currentUser);   //在session中设置用户
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				dbUtil.closeCon(con);    //关闭数据库连接
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
