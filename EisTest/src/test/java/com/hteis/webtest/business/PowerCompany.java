package com.hteis.webtest.business;

import java.util.Date;

import com.hteis.webtest.common.DateUtil;
import com.hteis.webtest.entities.Constants;
import com.hteis.webtest.entities.PowerCompanyData;
import com.hteis.webtest.pages.*;
import com.hteis.webtest.pages.cust.*;
import com.hteis.webtest.selenium.*;

public class PowerCompany {
	
	public static void create(PowerCompanyData data){
		//打开发电企业管理页面
		PowerCompanyPage companyPage = new PageBase().openPowerCompanyPage();
	
		//点击创建发电企业
		companyPage.getNewBtn().click();
		
		//输入基础信息数据
		PowerCompanyNewDialog dialog = new PowerCompanyNewDialog();
		dialog.setMainInfo(data.MainInfo);
		
		//点击保存按钮，验证提示信息：保存成功
		dialog.getMainInfoSaveBtn().click();
		companyPage.verifyAlert("保存成功");
		//companyPage.closeAlert();
		
		//点击联系人信息标签页
		dialog.getContactTab().click();
		
		//输入联系人信息
		dialog.setContactInfo(data.ContactInfo);
		
		//点击保存按钮，验证提示信息：保存成功
		dialog.getContactSaveBtn().click();
		companyPage.verifyAlert("保存成功");
		//companyPage.closeAlert();
		
		//点击发电信息标签页
		dialog.getEleInfoTab().click();
		
		//输入发电信息
		dialog.setEleInfo(data.EleInfo);
		
		//点击保存按钮，验证提示信息：保存成功
		dialog.getEleSaveBtn().click();
		companyPage.verifyAlert("保存成功");
		//companyPage.closeAlert();
		
		//点击关闭按钮
		dialog.getEleCloseBtn().click();
		Driver.wait(1000);
	}
	
	public static void createAndApprove(PowerCompanyData data) throws Exception{
		create(data);
		
		//点击提交按钮	
		PowerCompanyPage companyPage = new PowerCompanyPage();
		Date submitTime = companyPage.submitCompany(data.MainInfo.Name);
		
		//验证提示信息：送审成功
		companyPage.verifyAlert("送审成功");
		companyPage.closeAlert();
		
		//使用客户经理账号登录
		Driver.reLaunchBrowser();
		Driver.open(Constants.urlTcrm);
    	LoginPage loginPage = new LoginPage();
    	loginPage.login(Constants.custManagerUsr, Constants.custManagerPwd);
		
		//打开未处理任务页面，将表格翻到最后一页
		TaskActivePage activePage = new PageBase().openTaskPage();
		activePage.getPager().goLastPage();
		
		//找到刚刚生成的未处理任务
		HtmlRow row = activePage.getTask(submitTime);
		data.SubmitTime = DateUtil.getDateTimeFromString(row.getCellValue(TaskActivePage.ApplyTimeColNo));
		
		//点击处理任务
		row.getCell(TaskActivePage.OperationColNo).findElementByLinkText("处理任务").click();
		Driver.wait(1000);	
		
		//填写审批意见"通过"，选择同意，点击确定
		TaskDialog dialog = new TaskDialog();
		dialog.getCommentInput().input("通过");
		dialog.getAgreeRb().check();
		dialog.getConfirmBtn().click();	
		data.ApproveTime = new Date();
		Driver.wait(1000);
		activePage.verifyAlert("处理成功");
		activePage.closeAlert();		
	}
}
