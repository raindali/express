package org.expressme.example;

public class UserDomain {
	public boolean exist(String name) {
		User u = new User();
		u.queryForLong("", "");
		u.create();
		return false;
	}
}
