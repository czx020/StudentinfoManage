package com.sisheng.web;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.sisheng.dao.GradeDao;
import com.sisheng.model.Grade;
import com.sisheng.model.PageBean;
import com.sisheng.util.DbUtil;
import com.sisheng.util.JsonUtil;
import com.sisheng.util.ResponseUtil;

public class GradeListServlet extends HttpServlet {

	DbUtil dbUtil = new DbUtil();
	GradeDao gradeDao = new GradeDao();

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String page = request.getParameter("page");
		String rows = request.getParameter("rows");
		//查询是获得前台传来的gradeName
		String gradeName = request.getParameter("gradeName");
		if(gradeName==null){
			gradeName="";       //这里置为空串，GradeDao用isEmpty判断
		}
		Grade grade = new Grade();
		grade.setGradeName(gradeName);
		
		PageBean pageBean = new PageBean(Integer.parseInt(page),
				Integer.parseInt(rows));
		Connection con = null;
		try {
			con = dbUtil.getCon();
			JSONObject result = new JSONObject();
			JSONArray jsonArray = JsonUtil.formatRsToJsoarray(gradeDao
					.gradeList(con, pageBean,grade));
			int total = gradeDao.gradeCount(con,grade);
			result.put("rows", jsonArray);
			result.put("total", total);
			ResponseUtil.write(response, result);
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
