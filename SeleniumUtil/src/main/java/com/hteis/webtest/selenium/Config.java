package com.hteis.webtest.selenium;

public class Config {
	
	public static int waitTimeout;
	public static int pageLoadTimeout;
	public static BrowserType browserType;
	public static int stepInterval;
	public static String defaultDownloadFolder;
	
//	public static int waitTimeout = 15000; // 查找控件时的超时时间 (毫秒)
//	
//	public static int pageLoadTimeout = 15000; // 打开网页超时时间 (毫秒)
//
//	public static BrowserType browserType = BrowserType.Chrome; // 测试使用的浏览器类型
//
//	public static int stepInterval = 1000; // 步骤之间的间隔时间 (毫秒)

	public static void loadConfig(){
		System.out.println("加载配置");
		waitTimeout = 15000;
		pageLoadTimeout = 60000;
		browserType = BrowserType.Chrome;
		stepInterval = 200;
		defaultDownloadFolder = "D:\\temp";
	}
}
