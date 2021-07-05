package com.hteis.webtest.business;

import com.hteis.webtest.entities.*;
import com.hteis.webtest.pages.*;
import com.hteis.webtest.selenium.*;
import com.hteis.webtest.common.*;

public class User {
	
	public static UserInfo registerNewBuyer(){
		Driver.open("https://tuad.hteis.cn/login?service=https://tuid.hteis.cn/cas");
		RegisterPage regPage = new RegisterPage();
		regPage.getRegLink().click();		
		Driver.open("https://tuid.hteis.cn/public/userLogIn.html");
		
		UserInfo user = UserInfo.createBuyer();
		regPage.registerBuyer(user);
		
		PageBase successPage = new PageBase();
		successPage.findElementByPartialLinkText("https://tuid.hteis.cn/public/activate.html").click();
		
		Logger.logInfo(String.format("User %s is created", user.loginName));
		return user;
	}
}
