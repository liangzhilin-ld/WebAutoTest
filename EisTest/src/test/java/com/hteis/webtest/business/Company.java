package com.hteis.webtest.business;

import org.testng.Assert;

import com.hteis.webtest.common.Logger;
import com.hteis.webtest.entities.*;
import com.hteis.webtest.pages.*;
import com.hteis.webtest.selenium.*;

public class Company {
	
	/***创建一个新公司并提交审核*/
	public static void createBuyerCompany(CompanyData companyData) throws Exception{
		
		//CompanyData companyData = CompanyData.createCompanyData();
		
		//注册新用户
		companyData.Admin  = User.registerNewBuyer();
		Driver.wait(5000);
		
		//用新用户登录
		Driver.open(Constants.urlTcrm);
    	LoginPage loginPage = new LoginPage();
    	loginPage.login(companyData.Admin.loginName, companyData.Admin.password);    	
    	
    	//打开企业信息管理页面
		PageBase homePage = new PageBase();
		homePage.openNavMenu("公司资料管理", "企业信息管理");
		CompanyInfoMgmtPage cmpyPage = new CompanyInfoMgmtPage();
		
		//点击信息未完善图标	
		cmpyPage.getIncompleteBtn().click();
		
		//点击基础信息标签页，输入数据后保存
		cmpyPage.getMainInfoTab().click();
		CompanyMainInfoPage mainInfoPage = new CompanyMainInfoPage();
		mainInfoPage.setFieldValues(companyData.MainInfo);
		cmpyPage.getSaveBtn().click();
		
		//点击企业认证信息标签页，输入数据后保存
		cmpyPage.getEntInfoTab().click();
		CompanyRegInfoPage regInfoPage = new CompanyRegInfoPage();
		regInfoPage.setFieldValues(companyData.RegInfo);
		cmpyPage.getSaveBtn().click();

		//点击账号信息标签页，输入数据后保存
		cmpyPage.getAccInfoTab().click();
		CompanyAccInfoPage accPage = new CompanyAccInfoPage();
		accPage.setFieldValues(companyData.BankAccInfo);
		cmpyPage.getSaveBtn().click();		
	
		//点击主要联系人信息标签页，输入数据后保存
		cmpyPage.getContactInfoTab().click();
		CompanyContactPage contactPage = new CompanyContactPage();
		contactPage.setFieldValues(companyData.ContactInfo);
		cmpyPage.getSaveBtn().click();
		
		//点击用电信息标签页，创建用电信息后保存
		cmpyPage.getEleInfoTab().click();	
		cmpyPage.getAddEleInfoLink().click();
		Driver.wait(1000);
		CompanyEleInfoForm form1 = new CompanyEleInfoForm(cmpyPage.getEleInfoForms().get(0));
		form1.setFieldValues(companyData.EleInfo);
		form1.getSaveBtn().click();
		Driver.wait(1000);
		
		//点击提交按钮
		cmpyPage.getSubmitBtn().click();
		Driver.wait(1000);
		cmpyPage.verifyAlert("送审成功");
		cmpyPage.closeAlert();
		Logger.logInfo(String.format("Company %s is submitted", companyData.MainInfo.CompanyName));	
		
	}
	
	public static void createAndApproveBuyerCompany(CompanyData companyData) throws Exception{
		//1. 创建一个新购电公司并提交审核
		Company.createBuyerCompany(companyData);				
		
		//2. 使用售电主管账号登录
		Driver.reLaunchBrowser();
		Driver.open(Constants.urlTsep);
    	new LoginPage().login(Constants.saleTLUsr, Constants.saleTLPwd);
		
		//3. 打开待处理工作->未处理任务		
		new PageBase().openNavMenu("待处理工作", "未处理任务");
		Driver.wait(2000);
		
		//4. 通过申请人找到表中刚提交的审核任务，点击处理任务
		TaskActivePage taskPage = new TaskActivePage();		
		taskPage.getPager().goLastPage();	
		HtmlTable taskTable = taskPage.getTaskTable();
		HtmlRow taskRow = taskTable.findRow(TaskActivePage.ApplicantColNo, companyData.Admin.loginName);
		Assert.assertNotNull(taskRow, "公司资料提交后未找到任务");
		taskRow.getCell(TaskActivePage.OperationColNo).click();
		Driver.wait(1000);		
		
		//5. 填写审批意见"通过"，选择同意，点击确定
		TaskDialog dialog = new TaskDialog();
		dialog.getCommentInput().input("通过");
		dialog.getAgreeRb().check();
		dialog.getConfirmBtn().click();	
		Driver.wait(1000);	
		
		//验证提示信息：处理成功
		taskPage.verifyAlert("处理成功");
		taskPage.closeAlert();
	}
}
