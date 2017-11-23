package com.sisheng.model;

public class Grade {
	private int id;// °à¼¶±àºÅ
	private String gradeName;// °à¼¶Ãû³Æ
	private String gradeDesc;// °à¼¶ÃèÊö

	public Grade(){
	}
	
	public Grade(String gradeName, String gradeDesc) {
		super();
		this.gradeName = gradeName;
		this.gradeDesc = gradeDesc;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getGradeName() {
		return gradeName;
	}

	public void setGradeName(String gradeName) {
		this.gradeName = gradeName;
	}

	public String getGradeDesc() {
		return gradeDesc;
	}

	public void setGradeDesc(String gradeDesc) {
		this.gradeDesc = gradeDesc;
	}

}
