package com.hteis.webtest.testcases;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.openqa.selenium.Cookie;
import org.testng.*;
import org.testng.annotations.Test;

import com.hteis.webtest.entities.Constants;
import com.hteis.webtest.pages.LoginPage;
import com.hteis.webtest.pages.PageBase;
import com.hteis.webtest.selenium.*;


public class LoginTest extends TCBase {

	
	@Test(groups="General", priority=1, testName="登录成功",
			description="输入正确的用户名和密码后点登录按钮能成功登录")
	public void Pub_Login01_1() throws Exception{
		//1. 访问https://tuad.hteis.cn/
		//2. 输入有效的用户名和密码
		//3. 点击登录按钮
		//4. 验证：登录成功
		this.loginUid(Constants.saleAdminUsr, Constants.saleAdminPwd);		
	}
	
	@Test(groups="General", priority=2, testName="登录成功-按回车",
			description="输入正确的用户名和密码后按回车键可登录")
	public void Pub_Login01_2() throws Exception{
		//5. 访问https://tuid.hteis.cn/	
		Driver.open("https://tuid.hteis.cn/");
    	LoginPage loginPage = new LoginPage();
    	
    	//6. 输入有效的用户名和密码
    	loginPage.getUserInput().setText(Constants.saleAdminUsr);
    	loginPage.getPasswordInput().setText(Constants.saleAdminPwd);
    	
    	//7. 按回车键
    	Driver.pressEnter();
    	Driver.waitPageLoad();
    	
    	//8. 验证：登录成功
    	Assert.assertEquals("https://tuid.hteis.cn/views/home.html", Driver.getCurrentUrl(), "登录失败");   	
	}
	
	@Test(groups="General", priority=2, testName="重置输入",
			description="点击重置按钮后输入的用户名和密码清空")
	public void Pub_Login02() throws Exception{
		//1. 访问https://tuad.hteis.cn/
		Driver.open("https://tuid.hteis.cn/");
		LoginPage loginPage = new LoginPage();
		
		//2. 输入任意用户名和密码
		loginPage.getUserInput().setText(Constants.saleAdminUsr);
    	loginPage.getPasswordInput().setText(Constants.saleAdminPwd);
		
		//3. 点击重置按钮
    	loginPage.getResetBtn().click();    	   	
    	
		//4.验证：用户名和密码输入框被清空
    	Assert.assertEquals(loginPage.getUserInput().getText(), "");
    	Assert.assertEquals(loginPage.getPasswordInput().getText(), "");
	}
	
	@Test(groups="General", priority=2, testName="登录失败",
			description="输入错误、空密码不能登录; 输入错误、空用户名不能登录")
	public void Pub_Login03() throws Exception{
		//验证以下情况下不能登录，系统提示“用户名或密码错误”
		//1. 输入正确的用户名，错误的密码
		Driver.open("https://tuid.hteis.cn/");
		LoginPage loginPage = new LoginPage();
		loginPage.login("jsbuyer010", "jsbuyer010123");
		this.verifyErrorMessage(loginPage, "用户名或者密码错误");			
		
		//2. 输入正确的用户名，密码为空
		Driver.reLaunchBrowser();
		Driver.open("https://tuid.hteis.cn/");
		loginPage = new LoginPage();
		loginPage.login("jsbuyer011", "");
		this.verifyErrorMessage(loginPage, "必须录入密码。");	
		
		//3. 输入数据库中不存在的用户名，数据库中存在的密码
		loginPage.login("jsbuyer0129317048", "jsbuyer012");
		this.verifyErrorMessage(loginPage, "用户名或者密码错误");
		
		//4. 输入数据库中不存在的用户名，数据库中不存在的密码
		Driver.reLaunchBrowser();
		Driver.open("https://tuid.hteis.cn/");
		loginPage = new LoginPage();
		loginPage.login("jsbuyer0129317048", "jsbuyer010123");
		this.verifyErrorMessage(loginPage, "用户名或者密码错误");		
		
		//5. 输入空的用户名，数据库中存在的密码
		Driver.reLaunchBrowser();
		Driver.open("https://tuid.hteis.cn/");
		loginPage = new LoginPage();
		loginPage.login("", "jsbuyer015");
		this.verifyErrorMessage(loginPage, "必须录入用户名。");	
		
		loginPage.login("jsbuyer010", "jsbuyer010");
	}
	
	@Test(groups="General", priority=2, testName="记住我",
			description="登录时不勾选“记住我”，关闭浏览器再次访问时需要重新登录\r\n"
						+"登录时勾选“记住我”，关闭浏览器再次访问时不需要重新登录\r\n"
						+"检查身份验证Cookie时效为14天")
	public void Pub_Login07() throws Exception{
		//1. 访问https://tuad.hteis.cn/
		Driver.open("https://tuad.hteis.cn/");
		LoginPage loginPage = new LoginPage();		
		
		//2. 输入正确的用户名和密码，不勾选“记住我”，点击登录
		loginPage.getUserInput().setText("jsadmin");
		loginPage.getPasswordInput().setText("jsadmin");
		loginPage.getRememberMeCk().unCheck();
		loginPage.getLoginBtn().click();
		
		//3. 重新打开浏览器
		Driver.reLaunchBrowser();
		
		//4. 访问https://tuad.hteis.cn/
		Driver.open("https://tuad.hteis.cn/");
		Driver.waitPageLoad();				
		
		//5. 验证：系统未自动登录
		Assert.assertTrue(Driver.getCurrentUrl().startsWith("https://tuad.hteis.cn/login"));
		
		//6. 输入正确的用户名和密码，勾选“记住我”，点击登录
		loginPage = new LoginPage();
		loginPage.getUserInput().setText("jsadmin");
		loginPage.getPasswordInput().setText("jsadmin");
		loginPage.getRememberMeCk().check();
		loginPage.getLoginBtn().click();	
		
		//10.查看身份凭证Cookie有效期
		//11.验证：身份凭证Cookie有效期为14天
		Cookie tgcCookie = Driver.getCookie("TGC");
	    Assert.assertNotNull(tgcCookie);
		Calendar c1 =  Calendar.getInstance(); 
		c1.setTime(tgcCookie.getExpiry());
		Calendar c2 = Calendar.getInstance(); 
		c2.setTime(new Date());
		c2.add(Calendar.DAY_OF_MONTH, 14);		
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");  
		Assert.assertEquals(f.format(c1.getTime()), f.format(c2.getTime()), "登录凭证Cookie有效期不正确");
	
		//7. 重新打开浏览器
		Driver.reLaunchBrowser();
		
		//8. 访问https://tuad.hteis.cn/
		Driver.open("https://tuad.hteis.cn/login?service=https://tuid.hteis.cn/cas");
		Driver.addCookie(tgcCookie);
		Driver.open("https://tuad.hteis.cn/login?service=https://tuid.hteis.cn/cas");
		Driver.waitPageLoad();	
		
		//9. 验证：系统自动以上次登录的用户登录
		PageBase page = new PageBase();
		Assert.assertEquals(Driver.getCurrentUrl(), "https://tuid.hteis.cn/views/home.html");
		Assert.assertEquals(page.getLoginNameSpan().getText(), "极管理");
		
		//10. 登出以清除cookie
		page.logOut();
		
	}
	
	@Test(groups="General", priority=1, testName="登出",
			description="登出后回到登录首页\r\n"
						+"登出后身份验证Cookie被清除\r\n"
						+"访问需要登录的页面时需要重新登录")
	public void Pub_LogOut(){
		//1. 访问https://tuid.hteis.cn/
		Driver.open("https://tuid.hteis.cn/");			
		
		//2. 输入用户名密码、勾选“记住我”后点登录
		LoginPage loginPage = new LoginPage();
		loginPage.getUserInput().setText("jsadmin");
		loginPage.getPasswordInput().setText("jsadmin");
		loginPage.getRememberMeCk().check();
		loginPage.getLoginBtn().click();	
		
		//3. 点击页面右上角的登录用户图标，选择“退出”
		PageBase page = new PageBase();
		page.logOut();
		Driver.waitPageLoad();
		
		//4. 验证：系统返回到登录页面
		Assert.assertEquals(Driver.getCurrentUrl(), "https://tuad.hteis.cn/login?service=https://tuid.hteis.cn/cas");
		
		//5. 点击浏览器的后退按钮
		Driver.back();
		Driver.waitPageLoad();
		
		//6. 验证：系统自动跳转到登录页面
		Assert.assertEquals(Driver.getCurrentUrl(), "https://tuad.hteis.cn/login?service=https://tuid.hteis.cn/cas");
		
		//7. 重新打开浏览器
		//8. 验证：系统未自动登录
		Assert.assertNull(Driver.getCookie("TGC"));
	}

	
	private void verifyErrorMessage(LoginPage page, String errorMsg){
		HtmlElement msgDiv = page.getErrorMsgDiv();
		Assert.assertTrue(msgDiv.isDisplayed(), "错误消息未显示");
		Assert.assertEquals(msgDiv.getText(), errorMsg);
	}
}
