package com.groupT01.model;

public class GuideLoginUser {
	public String userID;
	public String password;
	public String username;
	public String name;
	
	public int GID;

	public int getGID() {
		return GID;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name=name;
	}
	
	public String getUserID() {
		return userID;
	}

	public String getPassword() {
		return password;
	}

	public String getUsername() {
		return username;
	}

	public void setGID(int id) {
		this.GID = id;
	}

	public void setUserName(String name) {
		this.username = name;
	}

	public void setUserID(String username) {
		this.userID = username;
	}

	public void setPassword(String Password) {
		this.password = Password;
	}
}
