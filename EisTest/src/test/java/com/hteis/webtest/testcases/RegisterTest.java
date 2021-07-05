package com.hteis.webtest.testcases;

import org.testng.*;
import org.testng.annotations.*;

import com.hteis.webtest.business.*;
import com.hteis.webtest.entities.*;
import com.hteis.webtest.pages.*;
import com.hteis.webtest.selenium.*;

public class RegisterTest extends TCBase {
	
	private String regUrl = Constants.urlTuid + "public/userRegist.html";
	
	@Test(groups="General", priority=3, testName="注册成功", description="所有信息填写正确时，注册成功")
	public void Pub_Reg01() throws Exception{
		UserInfo user = User.registerNewBuyer();
		Driver.reLaunchBrowser();
		this.LoginShop(user.loginName, user.password);		
	}
	
	@Test(groups="General", priority=3, testName="免责声明", description="免责声明页面跳转")
	public void Pub_Reg02() throws Exception{
		
		// 访问注册页面https://tuid.hteis.cn/public/userRegist.html
		Driver.open(regUrl);		
		
		// 点击免责声明		
		RegisterPage regPage = new RegisterPage();
		regPage.getAgreementLink().click();		
		Driver.waitPageLoad();
		
		// 验证：系统跳转到免责声明页面
		Assert.assertTrue(Driver.isUrlOpened("https://tuid.hteis.cn/public/agreeMent.html"), "免责声明页面未打开");
	}
	
	@Test(groups="General", priority=2, testName="填写信息错误或少填时注册失败", 
			description="必填信息未填，或填写不符合要求时注册会失败，同时网页上给出字段验证错误信息")
	public void Pub_Reg03() throws Exception{
		
		// 访问注册页面https://tuid.hteis.cn/public/userRegist.html
		Driver.open(regUrl);	
		
		// 2. 在以下情况下验证错误提示，错误情况下点提交按钮，验证提交未成功。
		// 1) 用户名未填，其他正确，字段验证信息为“用户名是必须的。”
		RegisterPage regPage = new RegisterPage();
		HtmlInput usrInput = regPage.getUserNameInput();
		usrInput.setText("user123");
		usrInput.clear();
		Assert.assertTrue(regPage.getUserNameReqErrorSpan().isDisplayed(), "用户名必填错误未显示");
		Assert.assertEquals(regPage.getUserNameReqErrorSpan().getText(), "用户名是必须的。");		
		
		// 2) 手机号未填，字段验证信息为“手机号码是必须的。”
		HtmlInput phoneInput = regPage.getPhoneInput();
		phoneInput.setText("123");
		phoneInput.clear();
		Assert.assertTrue(regPage.getPhoneReqErrorSpan().isDisplayed(), "手机号必填错误未显示");
		Assert.assertEquals(regPage.getPhoneReqErrorSpan().getText(), "手机号码是必须的");
		
		// 3) 填入5位数字手机号，字段验证信息为“手机号码长度不小于6”
		phoneInput.setText("12345");		
		Assert.assertTrue(regPage.getPhoneMinLenErrorSpan().isDisplayed(), "手机号最小长度错误未显示");
		Assert.assertEquals(regPage.getPhoneMinLenErrorSpan().getText(), "手机号码长度不小于6");		
		
		// 4) 填入12位数字手机号，其他正确，字段验证信息为“手机号码长度不大于11”
		phoneInput.setText("123456789012");
		Assert.assertTrue(regPage.getPhoneMaxLenErrorSpan().isDisplayed(), "手机号最大长度错误未显示");
		Assert.assertEquals(regPage.getPhoneMaxLenErrorSpan().getText(), "手机号码长度不大于11");
		
		// 5) 填入6位数字手机号，出现对勾
		phoneInput.setText("123456");
		Assert.assertTrue(regPage.getPhoneValidMark().isDisplayed(), "手机号正确时对勾未显示");
		
		// 6) 填入11位数字手机号，出现对勾
		phoneInput.setText("12345678901");
		Assert.assertTrue(regPage.getPhoneValidMark().isDisplayed(), "手机号正确时对勾未显示");
		
		// 7) 不填邮箱，字段验证信息为“邮箱地址是必须的”
		// 8) 填入不带@的邮箱，字段验证信息为“请填写正确的邮箱地址”
		HtmlInput emailInput = regPage.getEmailInput();
		emailInput.setText("abc");
		Assert.assertTrue(regPage.getEmailErrorSpan().isDisplayed(), "请填写正确的邮箱地址");
		emailInput.clear();
		Assert.assertTrue(regPage.getEmailReqErrorSpan().isDisplayed(), "Email必填错误未显示");
		Assert.assertEquals(regPage.getEmailReqErrorSpan().getText(), "邮箱地址是必须的");
			
		
		// 9) 所属省份未选择，字段验证信息为“所属省份是必须的”
		HtmlSelect provSelect = regPage.getProvinceSelect();
		provSelect.selectByText("北京");
		provSelect.selectByIndex(0);
		Assert.assertTrue(regPage.getProvinceReqErrorSpan().isDisplayed(), "省份必填错误未显示");
		Assert.assertEquals(regPage.getProvinceReqErrorSpan().getText(), "所属省份是必须的。");
		
		// 10) 密码未填，字段验证信息为“密码是必须的：6-12个字符，区分大小写”
		HtmlInput pwdInput = regPage.getPasswordInput();
		pwdInput.setText("abcde");
		Assert.assertTrue(regPage.getPwdErrorMark().isDisplayed(), "密码必填错误未显示");
		pwdInput.setText("abcdeabcdeabc");
		Assert.assertTrue(regPage.getPwdErrorMark().isDisplayed(), "密码必填错误未显示");
		
		// 11)确认密码未填，字段验证信息为“验证密码是必须的”
		HtmlInput conPwdInput = regPage.getConfirmPasswordInput();
		conPwdInput.setText("abcdefg");
		conPwdInput.clear();
		Assert.assertTrue(regPage.getConfirmPwdReqErrorSpan().isDisplayed(), "确认密码必填错误未显示");		
		
		// 12)密码和确认密码不一样，提交后提示“密码是必须的：6-12个字符，区分大小写 ”
		regPage.inputValidData();
		String pwd = pwdInput.getText();
		conPwdInput.setText(pwd + "1");
		regPage.getSubmitButton().click();
		this.verifyAlert(regPage, "确认密码和密码不同");
	
		// 13)手机验证码未填，提交注册按钮不可用
		conPwdInput.setText("San@idk2d");
		regPage.getValCodeInput().clear();
		Assert.assertFalse(regPage.getSubmitButton().isEnabled(), "手机验证码未填时提交按钮可用");
		
		// 14)未勾选“我已阅读并同意免责声明”，提交注册按钮不可用
		regPage.getValCodeInput().setText("1234");
		regPage.getAgreementCheckbox().unCheck();
		Assert.assertFalse(regPage.getSubmitButton().isEnabled(), "未勾选同意免责声明时提交按钮可用");		
	}	
	
	@Test(groups="General", priority=2, testName="重复用户名、手机、邮箱注册失败", enabled=false,
			description="当填写已使用过的用户名、手机或邮箱进行注册时注册会失败")	
	public void Pub_Reg04() throws Exception{
		
		// 访问注册页面https://tuid.hteis.cn/public/userRegist.html
		Driver.open(regUrl);	
		RegisterPage regPage = new RegisterPage();
		HtmlInput usrInput = regPage.getUserNameInput();
		HtmlInput phoneInput = regPage.getPhoneInput();
		HtmlInput emailInput = regPage.getEmailInput();
		HtmlButton submitBtn = regPage.getSubmitButton();
		regPage.inputValidData();

		// 已使用: sradmin  13812345100@qq.com    13812345100
		// 未使用: sradmin983649 sradmin983649@qq.com  19834847368
		// 使用已有的用户名、未使用的邮箱和手机号注册用户
		// 验证提示信息：用户名已存在
		usrInput.setText("sradmin");
		submitBtn.click();
		this.verifyAlert(regPage, String.format("用户名【%s】已经存在", "sradmin"));
		usrInput.setText("sradmin983649");
		
		// 使用已有的邮箱，未使用的用户名和手机注册用户
		// 验证提示信息：邮箱已被使用
		emailInput.setText("13812345100@qq.com");
		submitBtn.click();
		this.verifyAlert(regPage, String.format("邮箱【%s】已经使用", "13812345100@qq.com"));
		emailInput.setText("sradmin983649@qq.com");	
		
		// 使用已有的手机，未使用的用户名和邮箱注册用户
		// 验证提示信息：手机号已被注册		
		phoneInput.setText("13812345100");
		submitBtn.click();
		this.verifyAlert(regPage, String.format("手机号【%s】已经使用", "13812345100"));
	}
}
