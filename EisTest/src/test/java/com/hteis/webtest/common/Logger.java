package com.hteis.webtest.common;

public class Logger {

	public static void logInfo(String info){
		System.out.println(String.format("%s info: %s", DateUtil.getCurrentDateTimeStr(),info));
	}
}
