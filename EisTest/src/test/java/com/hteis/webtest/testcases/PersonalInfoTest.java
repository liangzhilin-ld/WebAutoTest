package com.hteis.webtest.testcases;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.hteis.webtest.entities.Constants;
import com.hteis.webtest.pages.*;
import com.hteis.webtest.selenium.Driver;

public class PersonalInfoTest extends TCBase {

	
	@Test(groups="General", priority=1, testName="修改个人信息-成功",
			description="个人信息填写符合要求时可提交成功")
	public void Sep_PersonalInfo01(){
		
		//1.访问https://tuad.hteis.cn/
		Driver.open(Constants.urlTshop);
		
		//2.输入用户名、密码，登录购电用户：
		//用户名：samplea
		//密码：samplea
		LoginPage loginPage = new LoginPage();
		loginPage.login(Constants.buyerTestUsr, Constants.buyerTestPwd);
		
		//3.点击个人中心，选择个人信息菜单
		PageBase page = new PageBase();
		PersonalInfoPage piPage = page.openPersonalInfo();	
		
		//5.输入/修改可填写信息
		//姓名：samplea
		//身份证号：110101199001011234
		//座机：010-88123400
		//手机号：18712341234
		//email：18712341234@qq.com
		String newName, newId, newPhone, newCellPhone, newEmail;
		if(piPage.getLoginName().equals("张强")){
			newName = "张二强";
			newId = "110125198808084652";
			newPhone = "010-85623225";
			newCellPhone = "18948752315";
			newEmail = "18948752315@qq.com";			
		}
		else{
			newName = "张强";
			newId = "110125198808084651";
			newPhone = "010-85623221";
			newCellPhone = "18948752318";
			newEmail = "18948752318@qq.com";
		}
		
		piPage.getNameInput().setText(newName);
		piPage.getIdInput().setText(newId);
		piPage.getPhoneInput().setText(newPhone);
		piPage.getCellPhoneInput().setText(newCellPhone);
		piPage.getEmailInput().setText(newEmail);
		
		//6.点击“保存”， 验证：提示保存成功
		piPage.getSaveBtn().click();
		this.verifyAlert(piPage, "保存成功");
		
		//7.刷新页面，验证个人信息修改成功
		Driver.refresh();
		Driver.waitPageLoad();
		
		Assert.assertTrue(piPage.getLoginIdInput().isReadOnly(), "登录名非只读");
		Assert.assertTrue(piPage.getCompanyNameInput().isReadOnly(), "公司非只读");
		Assert.assertTrue(piPage.getOrgNameInput().isReadOnly(), "组织非只读");
		Assert.assertEquals(piPage.getLoginIdInput().getText(), Constants.buyerTestUsr, "登录名不正确");
		Assert.assertEquals(piPage.getNameInput().getText(), newName, "姓名不正确");
		Assert.assertEquals(piPage.getIdInput().getText(),  newId, "身份证号不正确");
		Assert.assertEquals(piPage.getPhoneInput().getText(), newPhone, "座机号不正确");
		Assert.assertEquals(piPage.getCellPhoneInput().getText(), newCellPhone, "手机号不正确");
		Assert.assertEquals(piPage.getEmailInput().getText(), newEmail, "Email不正确");
		Assert.assertEquals(piPage.getOrgNameInput().getText(), "采购部", "组织不正确");
		Assert.assertEquals(piPage.getCompanyNameInput().getText(), "四川绿叶用电需求公司", "公司名称不正确");
	}
	
	@Test(groups="General", priority=3, testName="修改个人信息-失败",
			description="个人信息填写不符合要求时提交失败, 字段验证信息正确")
	public void Sep_PersonalInfo02(){
		//1.访问https://tuad.hteis.cn/
		//2.输入用户名、密码，登录购电用户：
		//用户名：samplea
		//密码：samplea
		ShopHomePage homePage = this.LoginShop(Constants.buyerTestUsr, Constants.buyerTestPwd);
		
		//3.点击导航栏内“用户中心”-“个人信息维护”
		PersonalInfoPage piPage = homePage.openPersonalInfo();
		
		//4.输入/修改姓名、身份证号、座机、手机号、email等可填写信息
		//5. 在以下情况下验证错误提示，错误情况下点保存按钮，验证保存未成功。
		//1) 手机号未填，其他正确，字段验证信息为“手机号是必须的。”
		piPage.getCellPhoneInput().clear();
		piPage.getSaveBtn().click();
		Assert.assertTrue(piPage.getCellPhoneReqErrorSpan().isDisplayed(), "手机必填错误未显示");
		Assert.assertEquals(piPage.getCellPhoneReqErrorSpan().getText(), "手机号是必须的。");
		
		//2) 邮箱未填，其他正确，字段验证信息为“邮箱是必须的。”
		piPage.getEmailInput().clear();
		Assert.assertTrue(piPage.getEmailReqErrorSpan().isDisplayed(), "Email必填错误未显示");
		//Assert.assertEquals(piPage.getEmailReqErrorSpan().getText(), "Email是必须的。");
		Assert.assertEquals(piPage.getEmailReqErrorSpan().getText(), "email是必须得");
		
		//3) 填入非数字型手机号，字段无法写入
		piPage.getCellPhoneInput().input("晚间abcde");
		Assert.assertEquals(piPage.getCellPhoneInput().getText(), "");			
		
		//4) 填入使用过的手机号后点保存，提示“手机号xxx已经使用"
		String usedPhone = "13812345201";
		piPage.getEmailInput().setText("18876353677@qq.com");	
		piPage.getCellPhoneInput().setText(usedPhone);
		piPage.getSaveBtn().click();
		this.verifyAlert(piPage, String.format("手机号【%s】已经使用", usedPhone));
		
		//5) 填入使用过的email后点保存，提示"email已经使用"
		String usedEmail = "13812345201@qq.com";
		piPage.getEmailInput().setText(usedEmail);	
		piPage.getCellPhoneInput().setText("18876377777");
		piPage.getSaveBtn().click();
		this.verifyAlert(piPage, String.format("邮箱【%s】已经使用", usedEmail));
	}
}
