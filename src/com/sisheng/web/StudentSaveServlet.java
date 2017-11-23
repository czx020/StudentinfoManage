package com.sisheng.web;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import com.sisheng.dao.StudentDao;
import com.sisheng.model.Student;
import com.sisheng.util.DbUtil;
import com.sisheng.util.ResponseUtil;
import com.sisheng.util.StringUtil;

public class StudentSaveServlet extends HttpServlet {

	DbUtil dbUtil = new DbUtil();
	StudentDao studentDao = new StudentDao();
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Student student =null;
		
		request.setCharacterEncoding("utf-8");
		String stuNo=request.getParameter("stuNo");
		String stuName=request.getParameter("stuName");
		String sex=request.getParameter("sex");
		String birthday=request.getParameter("birthday");
		String gradeId=request.getParameter("gradeId");
		String email=request.getParameter("email");
		String stuDesc=request.getParameter("stuDesc");
		String stuId=request.getParameter("stuId");
		
		try{
			student = new Student(stuNo,stuName,sex, birthday,Integer.parseInt(gradeId), email,stuDesc);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		if(!StringUtil.isEmpty(stuId)){
			student.setStuId(Integer.parseInt(stuId));
		}
		Connection con=null;
		try{
			con=dbUtil.getCon();
			int saveNums=0;
			JSONObject result=new JSONObject();
			if(!StringUtil.isEmpty(stuId)){
				saveNums=studentDao.studentModify(con, student);
				System.out.print("更改学生数量为"+saveNums);
			}else{
				saveNums=studentDao.studentAdd(con, student);
				System.out.print("增加学生数量为"+saveNums);
			}
			if(saveNums>0){
				result.put("success", "true");
			}else{
				result.put("success", "true");
				result.put("errorMsg", "保存失败");
			}
			ResponseUtil.write(response, result);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try {
				dbUtil.closeCon(con);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
}
