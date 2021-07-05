package com.hteis.webtest.testcases;

import org.testng.*;
import org.testng.annotations.Test;

import com.hteis.webtest.entities.Constants;
import com.hteis.webtest.pages.*;
import com.hteis.webtest.selenium.*;


public class ChangePwdTest extends TCBase {
	

	@Test(suiteName="General", groups="用户管理", priority=1, testName="修改密码-成功",
			description="输入正确后修改密码成功")
	public void Pub_ChangePwd01(){
		String newPwd = Constants.uidTestPwd + "1#3";
		
		//1. 访问https://tuid.hteis.cn/
		Driver.open(Constants.urlTuid);				
		
		//2. 输入用户名密码后点登录
		LoginPage loginPage = new LoginPage();
		loginPage.login(Constants.uidTestUsr, Constants.uidTestPwd);
		
		//3. 点击页面右上角的登录用户图标，选择“修改密码”
		//4. 选择修改密码		
		//5. 输入正确的原密码
		//6. 输入符合要求的新密码		
		//7. 新密码确认输入框内输入正确的新密码		
		//8. 点击确定修改按钮
		PageBase page = new PageBase();					
		page.changePassword(Constants.uidTestPwd, newPwd);
		
		//9. 验证：密码修改成功
		//10.退出系统回到登录页面
		page.logOut();
		
		//11.使用新密码登录		
		loginPage.login(Constants.uidTestUsr, newPwd);
		
		//12.验证：登录成功
		Assert.assertEquals(page.getLoginNameSpan().getText(), Constants.uidTestName);
		
		//  改回密码
		page.changePassword(newPwd, Constants.uidTestPwd);	
	}
	
}
