package com.javaeye.i2534;

import org.expressme.simplejdbc.NamingStrategy;

public class Naming {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		NamingStrategy naming = NamingStrategy.underlinedNamingStrategy;
		System.out.println(naming.clazzToTable("userInfoABC"));
		System.out.println(naming.tableToClazz("user_Info_A_BC"));
		System.out.println(naming.clazzToTable("_user_Info_A_BC"));
	}

}
