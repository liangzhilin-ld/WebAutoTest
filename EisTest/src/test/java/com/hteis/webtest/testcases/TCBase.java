package com.hteis.webtest.testcases;

import com.hteis.webtest.selenium.*;

import org.testng.Assert;
import org.testng.annotations.*;

import com.hteis.webtest.entities.Constants;
import com.hteis.webtest.pages.*;
import com.hteis.webtest.pages.cust.*;
import com.hteis.webtest.pages.mgc.*;


public abstract class TCBase{
	@BeforeSuite
	public void beforeSuite() throws Exception{
		System.out.println("开始执行BeforeSuite");
	}
	
	@AfterSuite
	public void afterSuite(){
		System.out.println("开始执行AfterSuite");
	}	
	
	@BeforeClass
    public void beforeClass() throws Exception {
        System.out.println("开始执行" + this.getClass().getSimpleName());        
    }

    @AfterClass
    public void afterClass() {
        System.out.println("结束执行" + this.getClass().getSimpleName());        
    }
    
    @BeforeMethod
    public void beforeTest() throws Exception{
    	System.out.println("执行beforeTest");
    	Driver.create();
    }
    
    @AfterMethod
	public void afterTest(){
		System.out.println("执行afterTest");
		Driver.quit();
		
	}
    
    public void loginUid(String userName, String password) {
    	Driver.open(Constants.urlTuid);
    	LoginPage loginPage = new LoginPage();
    	loginPage.login(userName, password);
    	Assert.assertEquals(Driver.getCurrentUrl(), Constants.urlTuid + "views/home.html", "登录到UID失败");   	
    }
    
    public SepHomePage loginSep(String userName, String password) {
    	Driver.open(Constants.urlTsep);
    	LoginPage loginPage = new LoginPage();
    	loginPage.login(userName, password);
    	Assert.assertEquals(Driver.getCurrentUrl(), Constants.urlTsep + "views/home.html", "登录到SEP失败"); 
    	return new SepHomePage();
    }
    
    public ShopHomePage LoginShop(String userName, String password){
    	Driver.open(Constants.urlTshop);
    	LoginPage loginPage = new LoginPage();
    	loginPage.login(userName, password);
    	Assert.assertEquals(Driver.getCurrentUrl(), Constants.urlTshop + "views/home.html", "登录到SHOP失败");
    	return new ShopHomePage();
    }
    
    public MgcHomePage loginMgc(String userName, String password) {
    	Driver.open(Constants.urlTmgc);
    	LoginPage loginPage = new LoginPage();
    	loginPage.login(userName, password);
    	Assert.assertEquals(Driver.getCurrentUrl(), Constants.urlTmgc + "views/home.html", "登录到MGC失败");
    	return new MgcHomePage();
    }
    
    public CustHomePage loginCust(String userName, String password) {
    	Driver.open(Constants.urlTcrm);
    	LoginPage loginPage = new LoginPage();
    	loginPage.login(userName, password);
    	return new CustHomePage();
    	//Assert.assertEquals(Driver.getCurrentUrl(), Constants.urlTcrm + "views/home.html", "登录到CUST失败");
    }
    
    public TcmHomePage loginTcm(String userName, String password) {
    	Driver.open(Constants.urlTtcm);
    	LoginPage loginPage = new LoginPage();
    	loginPage.login(userName, password);
    	Assert.assertEquals(Driver.getCurrentUrl(), Constants.urlTtcm + "views/home.html", "登录到TCM失败");
    	
    	return new TcmHomePage();
    }
    
    public void verifyAlert(HtmlPage page, String message){
    	String actualMsg = page.findElementByCss("span[ng-bind='alert.msg']").getText();
    	Assert.assertTrue(actualMsg.contains(message), "未发现提示消息：" + message + "；当前提示消息为：" + actualMsg);
    }
}
