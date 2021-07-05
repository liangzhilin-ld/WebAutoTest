package com.hteis.webtest.business;

import com.hteis.webtest.entities.ComplaintData;
import com.hteis.webtest.entities.Constants;
import com.hteis.webtest.pages.*;
import com.hteis.webtest.pages.cust.*;
import com.hteis.webtest.selenium.Driver;


public class CustComplaint {

	public static void create(ComplaintData data){
		//打开客户服务管理页面
		CustComplaintPage custPage = new PageBase().openCustComplaintPage();

		//点击创建投诉按钮，输入数据
		custPage.getNewBtn().click();
		CustComplaintNewDialog newDialog = new CustComplaintNewDialog();
		newDialog.setFieldValues(data);
		
		//点击保存按钮
		newDialog.getSaveBtn().click();
		//Driver.wait(1000);
		custPage.verifyAlert("保存成功");
		custPage.closeAlert();
	}
	
	public static void handle(ComplaintData data){
		//打开客户投诉处理页面
		CustComplaintPage custPage = new PageBase().openCustComplaintHandlePage();
		
		//点击处理
		CustComplaintHandleDialog handleDialog = custPage.openHandleDialog(data);
		
		//输入处理人、处理详情，选择客户反馈
		data.addHandleData();
		handleDialog.setFieldValues(data);
		
		//点击保存按钮，验证提示消息：保存成功
		handleDialog.getSaveBtn().click();
		custPage.verifyAlert("保存成功");
		custPage.closeAlert();
	}
	
	public static void createAndHandle(ComplaintData data) throws Exception{
		create(data);
		
		Driver.reLaunchBrowser();
		Driver.open(Constants.urlTtcm);
    	new LoginPage().login(Constants.custManagerUsr, Constants.custManagerPwd);
		handle(data);
	}
}
