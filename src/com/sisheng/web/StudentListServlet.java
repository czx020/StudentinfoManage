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

		System.out.println("����");
		//��ȡǰ̨�����Ĳ�ѯ����
		String stuNo=request.getParameter("stuNo");    //ѧ��
		String stuName=request.getParameter("stuName");  //����
		String sex=request.getParameter("sex");          //�Ա�
		String bbirthday=request.getParameter("bbirthday"); //��ʼ����
		String ebirthday=request.getParameter("ebirthday");  //��������
		String gradeId=request.getParameter("gradeId");  //�༶id
		
		//����һ��Student
		Student student=new Student();
		if(stuNo!=null){
			student.setStuNo(stuNo);
			student.setStuName(stuName);
			student.setSex(sex);
			if(!StringUtil.isEmpty(gradeId)){
				student.setGradeId(Integer.parseInt(gradeId));
			}
		}
		
		String page = request.getParameter("page"); // �ڼ�ҳ
		String rows = request.getParameter("rows"); // ÿҳ��¼��

		PageBean pageBean = new PageBean(Integer.parseInt(page),Integer.parseInt(rows));    //�õ�PageBean
		Connection con = null;

		try {
			con = dbUtil.getCon();
			JSONObject result = new JSONObject(); 
			JSONArray jsonArray = JsonUtil.formatRsToJsoarray(studentDao.StudentList(con, pageBean,student,bbirthday,ebirthday)); //��JSONArray��������
			int total = studentDao.studentCount(con,student,bbirthday,ebirthday);    //��ȡѧ������
			result.put("rows", jsonArray);      //��JSONArray������JSONObject��
			result.put("total", total);             //��total����Ҳ������JSONObject��
			ResponseUtil.write(response, result);       //������д��response
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
