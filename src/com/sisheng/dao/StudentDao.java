package com.sisheng.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import com.sisheng.model.PageBean;
import com.sisheng.model.Student;
import com.sisheng.util.DateUtil;
import com.sisheng.util.StringUtil;

public class StudentDao {

	/**
	 * 得到指定条目的学生信息List
	 * @param con
	 * @param pageBean
	 * @return
	 * @throws Exception
	 */
	
	//String stuNo,String stuName,String sex,String bbirthday
	public ResultSet StudentList(Connection con, PageBean pageBean,Student student,String ebirthday,String bbirthday)
			throws Exception {
		//这里改成"select * from t_student,t_grade where gradeId in(select id from t_grade)"是不行的
		//因为学生表里面没有gradeName,要获取两个表的所有字段，再有datagrid自己选择是最方便的
		//如过学生表的stuId属性名改成id也会出错
		StringBuffer sb = new StringBuffer("select * from t_student s,t_grade g where s.gradeId=g.id");
		
		//下面为查询条件配值
		if(!StringUtil.isEmpty(student.getStuNo())){     //学号模糊查询
			sb.append(" and s.stuNo like '%"+student.getStuNo()+"%'");
		}
		
		if(!StringUtil.isEmpty(student.getStuName())){   //姓名模糊查询
			sb.append(" and s.stuName like '%"+student.getStuName()+"%'");
		}
		
		if(!StringUtil.isEmpty(student.getSex())){   //性别模糊查询
			sb.append(" and s.sex ='"+student.getSex()+"'");
		}
		
		if(student.getGradeId()!=-1){
			sb.append(" and s.gradeId ="+student.getGradeId());
		}
		
		if(!StringUtil.isEmpty(bbirthday)){   //开始日期
			sb.append(" and TO_DAYS(s.birthday) >=TO_DAYS('"+bbirthday+"')");
		}
		
		if(!StringUtil.isEmpty(bbirthday)){   //结束日期
			sb.append(" and TO_DAYS(s.birthday) <=TO_DAYS('"+ebirthday+"')");
		}
		
		if (pageBean != null) {
			sb.append(" limit " + pageBean.getStart() + ","+ pageBean.getRows());
		}
		System.out.println(sb.toString());
		PreparedStatement pstmt = con.prepareStatement(sb.toString());
		return pstmt.executeQuery();
	}
	
	public int studentCount(Connection con,Student student,String bbirthday,String ebirthday) throws Exception{
		StringBuffer sb = new StringBuffer("select count(*) as total from t_student s,t_grade g where s.gradeId=g.id");
		//下面为查询条件配值
				if(!StringUtil.isEmpty(student.getStuNo())){     //学号模糊查询
					sb.append(" and s.stuNo like '%"+student.getStuNo()+"%'");
				}
				
				if(!StringUtil.isEmpty(student.getStuName())){   //姓名模糊查询
					sb.append(" and s.stuName like '%"+student.getStuName()+"%'");
				}
				
				if(!StringUtil.isEmpty(student.getSex())){   //性别模糊查询
					sb.append(" and s.sex ='"+student.getSex()+"'");
				}
				
				if(student.getGradeId()!=-1){
					sb.append(" and s.gradeId ="+student.getGradeId());
				}
				
				if(!StringUtil.isEmpty(bbirthday)){   //开始日期
					sb.append(" and TO_DAYS(s.birthday) >=TO_DAYS('"+bbirthday+"')");
				}
				
				if(!StringUtil.isEmpty(bbirthday)){   //结束日期
					sb.append(" and TO_DAYS(s.birthday) <=TO_DAYS('"+ebirthday+"')");
				}
		PreparedStatement pstmt = con.prepareStatement(sb.toString());
		ResultSet rs = pstmt.executeQuery();
		if(rs.next()){
			return rs.getInt("total");
		}else{
			return 0;
		}
	}
	
	/**
	 * 删除指定学生
	 * @param con
	 * @param delIds
	 * @return
	 * @throws Exception
	 */
	public int studentDelete(Connection con,String delIds) throws Exception{
		String sql = "delete from t_student where stuId in("+delIds+")";
		PreparedStatement pstmt =con.prepareStatement(sql);
		return pstmt.executeUpdate();
	}
	
	public int studentAdd(Connection con,Student student) throws Exception{
		String sql="insert into t_student values(null,?,?,?,?,?,?,?)";
		PreparedStatement pstmt=con.prepareStatement(sql);
		pstmt.setString(1, student.getStuNo());
		pstmt.setString(2, student.getStuName());
		pstmt.setString(3, student.getSex());
		pstmt.setString(4, DateUtil.formatDate(DateUtil.formatString(student.getBirthday(), "yyyy-MM-dd"),"yyyy-MM-dd"));
		pstmt.setInt(5, student.getGradeId());
		pstmt.setString(6, student.getEmail());
		pstmt.setString(7, student.getStuDesc());
		return pstmt.executeUpdate();
	}

	public int studentModify(Connection con, Student student) throws Exception {
		String sql="update t_student set stuNo=?,stuName=?,sex=?,birthday=?,gradeId=?,email=?,stuDesc=? where stuId=?";
		PreparedStatement pstmt=con.prepareStatement(sql);
		pstmt.setString(1, student.getStuNo());
		pstmt.setString(2, student.getStuName());
		pstmt.setString(3, student.getSex());
		pstmt.setString(4, DateUtil.formatDate(DateUtil.formatString(student.getBirthday(), "yyyy-MM-dd"),"yyyy-MM-dd"));
		pstmt.setInt(5, student.getGradeId());
		pstmt.setString(6, student.getEmail());
		pstmt.setString(7, student.getStuDesc());
		pstmt.setInt(8, student.getStuId());
		return pstmt.executeUpdate();
	}
}
