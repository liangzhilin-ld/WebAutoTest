package com.hteis.webtest.business;

import com.hteis.webtest.common.Logger;
import com.hteis.webtest.entities.Constants;
import com.hteis.webtest.entities.TradeFairData;
import com.hteis.webtest.pages.*;
import com.hteis.webtest.pages.trade.*;
import com.hteis.webtest.selenium.*;

public class TradeFair {

	public static void create(TradeFairData data){

		//打开交易管理->交易场次管理
		TradeFairPage tradeFairPage = new PageBase().openTradeFairPage();
		
		//点击新增按钮
		tradeFairPage.getAddLink().click();
		
		//输入月度竞价交易数据
		TradeFairNewPage newPage = new TradeFairNewPage();
		newPage.setFieldValues(data);
		
		//点击确定按钮
		newPage.getConfirmBtn().click();
		
		//验证提示消息：保存成功
		newPage.verifyAlert("保存成功");
		newPage.closeAlert();
	}
	
	public static void createAndSubmit(TradeFairData data){
		create(data);
		
		TradeFairPage tradeFairPage = new TradeFairPage();
		HtmlRow row = tradeFairPage.getTradeFairTable().findRow(TradeFairPage.NameColNo, data.Name);
		row.getCell(TradeFairPage.OpsColNo).findElementByLinkText("提交").click();
		tradeFairPage.verifyAlert("提交成功");
		tradeFairPage.closeAlert();
		
		Logger.logInfo("交易场次创建成功：" + data.Name);
	}
	
	public static void createAndApprove(TradeFairData tradeFairData) throws Exception{
		createAndSubmit(tradeFairData);

		// 使用交易管理员登录
		Driver.reLaunchBrowser();
		Driver.open(Constants.urlTtcm);
		new LoginPage().login(Constants.tradeTLUsr, Constants.tradeTLPwd);

		// 打开待处理页面
		TaskActivePage taskPage = new PageBase().openTaskPage();
		Driver.wait(2000);

		// 找到最后一条任务，点击处理任务
		HtmlTable taskTable = taskPage.getTaskTable();
		HtmlNgPager pager = taskPage.getPager();
		pager.goLastPage();
		Driver.wait(1000);
		taskTable.getLastRow().getCell(TaskActivePage.OperationColNo).findElementByLinkText("处理任务").click();
		Driver.wait(1000);

		// 通过审批
		TaskDialog dialog = new TaskDialog();
		dialog.getCommentInput().input("同意发布");
		dialog.getAgreeRb().check();
		dialog.getConfirmBtn().click();
		Driver.wait(1000);
		taskPage.verifyAlert("处理成功");
		//taskPage.closeAlert();
	}
}
