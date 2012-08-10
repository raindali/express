package org.expressme.examples.typecho.utils;

import java.util.Calendar;
import java.util.Date;

public class TypechoUtils {

	public static int getNow() {
		return Long.valueOf(System.currentTimeMillis() / 1000).intValue();
	}

	public static Date time(int time) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(time * 1000);
		return cal.getTime();
	}
}
