package org.expressme.modules.utils;

import java.io.UnsupportedEncodingException;

public class StringUtils {

	public static String subStringByByte(String orignal, int subcount) throws UnsupportedEncodingException {
		int reInt = 0;
		String reStr = "";
		if (orignal == null)
			return "";
		char[] tempChar = orignal.toCharArray();
		for (int kk = 0; (kk < tempChar.length && subcount > reInt); kk++) {
			String s1 = orignal.valueOf(tempChar[kk]);
			byte[] b = s1.getBytes("GBK");
			reInt += b.length;
			reStr += tempChar[kk];
		}
		return reStr;
	}
	
	public static void main(String[] args) throws UnsupportedEncodingException {
		System.out.println(subStringByByte("中華ABCD人民共和國感謝法", 6));
	}
}
