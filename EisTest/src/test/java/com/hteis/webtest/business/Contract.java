package com.hteis.webtest.business;

import com.hteis.webtest.entities.Constants;
import com.hteis.webtest.entities.ContractData;
import com.hteis.webtest.pages.*;
import com.hteis.webtest.selenium.*;

public class Contract {

	public static void create(ContractData contractData){
		ContractListPage listPage = new PageBase().openContractListPage();
		listPage.getNewContractBtn().click();
			
		ContractNewPage newPage = new ContractNewPage();
		newPage.setFieldValues(contractData);		
		
		newPage.getConfirmBtn().click();
		Driver.wait(1000);
		newPage.verifyAlert("保存成功");
		newPage.closeAlert();
	}
	
	public static void createAndSubmit(ContractData contractData){
		create(contractData);
		
		ContractListPage listPage = new ContractListPage();
		listPage.getContractTable()
		.findRow(ContractListPage.CustNameColNo, contractData.CustName)
		.getCell(ContractListPage.ContractIdColNo)
		.findElementByTag("a")
		.click();
		Driver.wait(1000);
	
		//在编辑合同页面中点击提交按钮
		ContractNewPage newPage = new ContractNewPage();
		newPage.getSubmitBtn().click();
		Driver.wait(1000);
		newPage.verifyAlert("提交成功");
		//newPage.closeAlert();
	}
	
	public static void createAndApprove(ContractData contractData) throws Exception{
		createAndSubmit(contractData);
		
		Driver.reLaunchBrowser();
		Driver.open(Constants.urlTsep);
    	new LoginPage().login(Constants.saleTLUsr, Constants.saleTLPwd);
    	
    	Approve();   	
	}
	
	public static void Approve(){
		//打开未处理任务页面
		TaskActivePage taskPage = new PageBase().openTaskPage();
		
		//找到最后一条任务，点击处理任务		
		HtmlTable taskTable = taskPage.getTaskTable();
		HtmlNgPager pager = taskPage.getPager();
		pager.goLastPage();
		Driver.wait(1000);
		taskTable.getLastRow().getCell(TaskActivePage.OperationColNo).findElementByLinkText("处理任务").click();
		Driver.wait(1000);
		
		//输入审核意见"同意发布"
		TaskDialog dialog = new TaskDialog();
		dialog.getCommentInput().input("同意发布");
		
		//选择审核结论同意 
		dialog.getAgreeRb().check();
		
		//点击确定
		dialog.getConfirmBtn().click();
		Driver.wait(1000);
		taskPage.verifyAlert("处理成功");
		//taskPage.closeAlert();
	}
	
	public static void Confirm(String loginName, String password) throws Exception{
		//使用购电用户登录
		Driver.reLaunchBrowser();
		Driver.open(Constants.urlTshop);
		new LoginPage().login(loginName, password);		
		
		//打开未处理任务页面，找到最后一个任务，点击处理任务
		TaskActivePage taskPage = new PageBase().openTaskPage();
		HtmlTable taskTable = taskPage.getTaskTable();
		HtmlNgPager pager = taskPage.getPager();
		pager.goLastPage();
		Driver.wait(1000);
		taskTable.getLastRow().getCell(TaskActivePage.OperationColNo).findElementByLinkText("处理任务").click();
		Driver.wait(1000);	
		
		TaskDialog dialog = new TaskDialog();
		dialog.getCommentInput().input("确认无误");
		dialog.getAgreeRb().check();
		dialog.getConfirmBtn().click();
		Driver.wait(1000);
		taskPage.verifyAlert( "处理成功");
		taskPage.closeAlert();
	}
}
