package com.sisheng.model;

public class User {
	private int id; // �û�id
	private String userName; // �û��˺�
	private String password; // �û�����

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return userName;
	}

	public void setUsername(String username) {
		this.userName = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public User(String username, String password) {
		this.userName = username;
		this.password = password;
	}

	public User() {
	}
}
