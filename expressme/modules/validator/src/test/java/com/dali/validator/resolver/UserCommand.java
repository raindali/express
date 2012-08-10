package com.dali.validator.resolver;

public class UserCommand {
	private User user;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
}

class User {
	public String getLogUsername() {
		return logUsername;
	}
	public void setLogUsername(String logUsername) {
		this.logUsername = logUsername;
	}
	public String getLogPassword() {
		return logPassword;
	}
	public void setLogPassword(String logPassword) {
		this.logPassword = logPassword;
	}
	public String getDeptCode() {
		return deptCode;
	}
	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}
	private String logUsername;
	private String logPassword;
	private String deptCode;
}