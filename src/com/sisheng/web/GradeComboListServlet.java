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
import com.sisheng.util.DbUtil;
import com.sisheng.util.JsonUtil;
import com.sisheng.util.ResponseUtil;

public class GradeComboListServlet extends HttpServlet {

	DbUtil dbUtil = new DbUtil();
	GradeDao gradeDao = new GradeDao();
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Connection con = null;
		try{
			con=dbUtil.getCon();
			JSONObject jsonObject = new JSONObject();
			JSONArray jsonArray = new JSONArray();
			jsonObject.put("id", "");
			jsonObject.put("gradeName", "请选择");
			jsonArray.add(jsonObject);
			jsonArray.addAll(JsonUtil.formatRsToJsoarray(gradeDao.gradeList(con, null, null))); //记得这里是addAll!坑爹
			ResponseUtil.write(response, jsonArray);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
				dbUtil.closeCon(con);
			}catch(Exception w){
				w.printStackTrace();
			}
		}
	}
}
