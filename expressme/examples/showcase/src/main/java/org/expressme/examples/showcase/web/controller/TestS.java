package org.expressme.examples.showcase.web.controller;

public class TestS {
	public static void main(String[] args) {
		String str = new String("456");
		new TestS().testname(str);
		System.out.println(str);
	}
	public void testname(String str) {
		str = "123";
	}
	
}
