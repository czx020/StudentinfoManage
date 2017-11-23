package com.sisheng.web;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.sisheng.dao.StudentDao;
import com.sisheng.model.PageBean;
import com.sisheng.model.Student;
import com.sisheng.util.DbUtil;
import com.sisheng.util.JsonUtil;
import com.sisheng.util.ResponseUtil;
import com.sisheng.util.StringUtil;

public class StudentListServlet extends HttpServlet {

	DbUtil dbUtil = new DbUtil();
	StudentDao studentDao = new StudentDao();

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		System.out.println("有啦");
		//获取前台传来的查询数据
		String stuNo=request.getParameter("stuNo");    //学号
		String stuName=request.getParameter("stuName");  //姓名
		String sex=request.getParameter("sex");          //性别
		String bbirthday=request.getParameter("bbirthday"); //开始日期
		String ebirthday=request.getParameter("ebirthday");  //结束日期
		String gradeId=request.getParameter("gradeId");  //班级id
		
		//构造一个Student
		Student student=new Student();
		if(stuNo!=null){
			student.setStuNo(stuNo);
			student.setStuName(stuName);
			student.setSex(sex);
			if(!StringUtil.isEmpty(gradeId)){
				student.setGradeId(Integer.parseInt(gradeId));
			}
		}
		
		String page = request.getParameter("page"); // 第几页
		String rows = request.getParameter("rows"); // 每页记录数

		PageBean pageBean = new PageBean(Integer.parseInt(page),Integer.parseInt(rows));    //得到PageBean
		Connection con = null;

		try {
			con = dbUtil.getCon();
			JSONObject result = new JSONObject(); 
			JSONArray jsonArray = JsonUtil.formatRsToJsoarray(studentDao.StudentList(con, pageBean,student,bbirthday,ebirthday)); //用JSONArray返回数据
			int total = studentDao.studentCount(con,student,bbirthday,ebirthday);    //获取学生总数
			result.put("rows", jsonArray);      //把JSONArray放入结果JSONObject中
			result.put("total", total);             //把total总数也放入结果JSONObject中
			ResponseUtil.write(response, result);       //把数据写入response
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				dbUtil.closeCon(con);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
