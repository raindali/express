package org.expressme.bind2;

import javax.servlet.ServletRequest;

public class Test {
	
	public static void main(String[] args) {
		ServletRequest request = new MockServletRequest();
		User u = new BindUtils().bind(request, User.class);
		System.out.println(u.getName());
	}

}
