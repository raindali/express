package org.expressme.example;

public class UserManager {

	public void check() {
		User user = new User();
		user.setId(123);
		user.setName("xyz");

		user.create();
		user.updateEntity();
		int i = user.queryForInt("select count(*) from User where name like ?", "%kkk%");
		
	}
}
