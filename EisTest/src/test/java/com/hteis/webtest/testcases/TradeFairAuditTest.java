package com.hteis.webtest.testcases;

import org.testng.annotations.Test;

import java.util.Date;

import org.testng.*;

import com.hteis.webtest.business.TradeFair;
import com.hteis.webtest.entities.*;
import com.hteis.webtest.pages.*;
import com.hteis.webtest.pages.trade.*;
import com.hteis.webtest.selenium.*;

public class TradeFairAuditTest extends TCBase {
	@Test(suiteName = "Sep", groups = "交易管理", priority = 1, testName = "提交月度竞价交易场次", 
			description = "验证交易场次编辑页面可提交审核\r\n"
			+ "交易场次提交后状态变为审核中\r\n" 
			+ "交易场次提交后流转历史里的记录正确\r\n" 
			+ "交易场次提交后点击场次名称打开的查看页面内数据正确")
	public void Sep_TradeFairSubmit01() throws Exception {
		// 使用交易员登录https://ttcm.hteis.cn/
		TcmHomePage homePage = this.loginTcm(Constants.tradeTestUsr, Constants.tradeTestPwd);

		// 创建一个月度竞价交易场次
		TradeFairData tradeFairData = TradeFairData.createMonthBidData(Constants.tradeCenterName1);
		TradeFair.create(tradeFairData);

		// 点击交易场次名称打开编辑页面
		TradeFairPage tradeFairPage = homePage.openTradeFairPage();
		tradeFairPage.openTradeFair(tradeFairData.Name);

		// 点击提交按钮
		TradeFairEditPage editPage = new TradeFairEditPage();
		editPage.getSubmitBtn().click();
		Driver.wait(1000);

		// 验证提示信息：提交成功
		editPage.verifyAlert("提交成功");
		tradeFairData.Status = "审核中";

		// 验证表中交易场次的状态是“审核中”
		HtmlRow row = tradeFairPage.getTradeFairTable().findRow(TradeFairPage.NameColNo, tradeFairData.Name);
		Assert.assertEquals(row.getCellValue(TradeFairPage.StatusColNo), "审核中");

		// 点击流转历史
		row.getCell(TradeFairPage.OpsColNo).findElementByLinkText("流转历史").click();
		Driver.wait(1000);

		// 验证流转历史中只有一条记录，处理人和处理时间正确
		ViewHistoryDialog dialog = new ViewHistoryDialog();
		FlowStepData[] expectedSteps = new FlowStepData[] {
				new FlowStepData(1, Constants.tradeTestName, new Date(), "", "") };
		dialog.verifyFlowStepData(expectedSteps);

		// 关闭流转历史
		dialog.getCloseBtn().click();
		Driver.wait(1000);

		// 点击场次名称，验证打开的是查看页面
		tradeFairPage.openTradeFair(tradeFairData.Name);
		Assert.assertTrue(Driver.getCurrentUrl().startsWith(Constants.urlTtcm + "views/home.html#/numpre/"),
				"提交后交易场次打开后显示的不是查看页面,当前页面：" + Driver.getCurrentUrl());

		// 验证页面内数据正确
		TradeFairViewPage viewPage = new TradeFairViewPage();
		viewPage.verifyData(tradeFairData);

		// 点击返回，验证返回到交易场次管理页面
		viewPage.getBackBtn().click();
		Driver.wait(1000);
		Assert.assertEquals(Driver.getCurrentUrl(), Constants.urlTtcm + "views/home.html#/nummanage");

	}

	@Test(suiteName = "Sep", groups = "交易管理", priority = 1, testName = "提交年度协商交易场次", 
			description = "验证交易场次表格中可提交审核\r\n"
				+ "交易场次提交后状态变为审核中\r\n" 
				+ "交易场次提交后流转历史里的记录正确\r\n" 
				+ "交易场次提交后点击场次名称打开的查看页面内数据正确")
	public void Sep_TradeFairSubmit02() throws Exception {
		// 使用交易员登录https://ttcm.hteis.cn/
		this.loginTcm(Constants.tradeTestUsr, Constants.tradeTestPwd);

		// 创建一个多月竞价交易场次
		TradeFairData tradeFairData = TradeFairData.createYearNegotiateData(Constants.tradeCenterName1);
		TradeFair.create(tradeFairData);

		// 点击操作“提交”
		TradeFairPage tradeFairPage = new TradeFairPage();
		HtmlRow row = tradeFairPage.getTradeFairTable().findRow(TradeFairPage.NameColNo, tradeFairData.Name);
		row.getCell(TradeFairPage.OpsColNo).findElementByLinkText("提交").click();
		Driver.wait(1000);

		// 验证提示信息：提交成功
		tradeFairPage.verifyAlert("提交成功");
		tradeFairData.Status = "审核中";

		// 验证表中交易场次的状态是“审核中”
		row = tradeFairPage.getTradeFairTable().findRow(TradeFairPage.NameColNo, tradeFairData.Name);
		Assert.assertEquals(row.getCellValue(TradeFairPage.StatusColNo), "审核中");

		// 点击流转历史
		row.getCell(TradeFairPage.OpsColNo).findElementByLinkText("流转历史").click();
		Driver.wait(1000);

		// 验证流转历史中只有一条记录，处理人和处理时间正确
		ViewHistoryDialog dialog = new ViewHistoryDialog();
		FlowStepData[] expectedSteps = new FlowStepData[] {
				new FlowStepData(1, Constants.tradeTestName, new Date(), "", "") };
		dialog.verifyFlowStepData(expectedSteps);

		// 关闭流转历史
		dialog.getCloseBtn().click();
		Driver.wait(1000);

		// 点击场次名称，验证打开的是查看页面
		tradeFairPage.openTradeFair(tradeFairData.Name);
		Assert.assertTrue(Driver.isUrlOpened(Constants.urlTtcm + "views/home.html#/numpre/", false),
				"提交后交易场次打开后显示的不是查看页面,当前页面：" + Driver.getCurrentUrl());

		// 验证页面内数据正确
		TradeFairViewPage viewPage = new TradeFairViewPage();
		viewPage.verifyData(tradeFairData);

		// 点击返回，验证返回到交易场次管理页面
		viewPage.getBackBtn().click();
		Driver.wait(1000);
		Assert.assertEquals(Driver.getCurrentUrl(), Constants.urlTtcm + "views/home.html#/nummanage");
	}
	
	@Test(suiteName = "Sep", groups = "交易管理", priority = 1, testName = "提交多月竞价交易场次", 
			description = "验证交易场次表格中可提交审核\r\n"
				+ "交易场次提交后状态变为审核中\r\n" 
				+ "交易场次提交后流转历史里的记录正确\r\n" 
				+ "交易场次提交后点击场次名称打开的查看页面内数据正确")
	public void Sep_TradeFairSubmit03() throws Exception {
		// 使用交易员登录https://ttcm.hteis.cn/
		this.loginTcm(Constants.tradeTestUsr, Constants.tradeTestPwd);

		// 创建一个多月竞价交易场次
		TradeFairData tradeFairData = TradeFairData.createMultiMonthBidData(Constants.tradeCenterName1);
		TradeFair.create(tradeFairData);

		// 点击操作“提交”
		TradeFairPage tradeFairPage = new TradeFairPage();
		HtmlRow row = tradeFairPage.getTradeFairTable().findRow(TradeFairPage.NameColNo, tradeFairData.Name);
		row.getCell(TradeFairPage.OpsColNo).findElementByLinkText("提交").click();
		Driver.wait(1000);

		// 验证提示信息：提交成功
		tradeFairPage.verifyAlert("提交成功");
		tradeFairData.Status = "审核中";

		// 验证表中交易场次的状态是“审核中”
		row = tradeFairPage.getTradeFairTable().findRow(TradeFairPage.NameColNo, tradeFairData.Name);
		Assert.assertEquals(row.getCellValue(TradeFairPage.StatusColNo), "审核中");

		// 点击流转历史
		row.getCell(TradeFairPage.OpsColNo).findElementByLinkText("流转历史").click();
		Driver.wait(1000);

		// 验证流转历史中只有一条记录，处理人和处理时间正确
		ViewHistoryDialog dialog = new ViewHistoryDialog();
		FlowStepData[] expectedSteps = new FlowStepData[] {
				new FlowStepData(1, Constants.tradeTestName, new Date(), "", "") };
		dialog.verifyFlowStepData(expectedSteps);

		// 关闭流转历史
		dialog.getCloseBtn().click();
		Driver.wait(1000);

		// 点击场次名称，验证打开的是查看页面
		tradeFairPage.openTradeFair(tradeFairData.Name);
		Assert.assertTrue(Driver.isUrlOpened(Constants.urlTtcm + "views/home.html#/numpre/", false),
				"提交后交易场次打开后显示的不是查看页面,当前页面：" + Driver.getCurrentUrl());

		// 验证页面内数据正确
		TradeFairViewPage viewPage = new TradeFairViewPage();
		viewPage.verifyData(tradeFairData);

		// 点击返回，验证返回到交易场次管理页面
		viewPage.getBackBtn().click();
		Driver.wait(1000);
		Assert.assertEquals(Driver.getCurrentUrl(), Constants.urlTtcm + "views/home.html#/nummanage");
	}

	@Test(groups = "Sep", priority = 1, testName = "审核交易场次-通过", 
			description = "交易场次提交后交易主管未处理任务中可看到申请\r\n"
					+ "验证处理任务对话框中的数据正确\r\n" 
					+ "审核后任务被移出未处理任务\r\n" 
					+ "审核通过后交易场次状态为已发布\r\n" 
					+ "交易场次审核通过后点击场次名称打开的是查看页面\r\n"
					+ "审核通过后流转历史里的数据正确")
	public void Sep_TradeFairAudit01() throws Exception {

		// 使用交易员登录https://ttcm.hteis.cn/
		this.loginTcm(Constants.tradeTestUsr, Constants.tradeTestPwd);

		// 创建一个月度竞价交易场次并提交
		TradeFairData tradeFairData = TradeFairData.createMonthBidData(Constants.tradeCenterName1);
		TradeFair.createAndSubmit(tradeFairData);

		// 使用交易管理员登录
		Driver.reLaunchBrowser();
		TcmHomePage homePage = this.loginTcm(Constants.tradeTLUsr, Constants.tradeTLPwd);

		// 打开待处理页面
		TaskActivePage taskPage = homePage.openTaskPage();
		Driver.wait(2000);

		// 找到最后一条任务，点击处理任务
		HtmlTable taskTable = taskPage.getTaskTable();
		HtmlNgPager pager = taskPage.getPager();
		pager.goLastPage();
		int rowCount = taskTable.getRowCount();
		taskTable.getLastRow().getCell(TaskActivePage.OperationColNo).findElementByLinkText("处理任务").click();
		Driver.wait(3000);

		// 验证对话框中的交易场次数据正确
		TaskDialog dialog = new TaskDialog();
		dialog.verifyTradeFairData(tradeFairData);

		// 点击查看原始单据，验证交易场次详细信息页面弹出
		dialog.getOrigDocLink().click();
		Driver.wait(3000);
		Assert.assertTrue(Driver.isUrlOpened(Constants.urlTsep + "views/subPage.html#/numpre/", false),
				"未打开交易场次详细信息页面");
		Driver.closeWindow();

		// 输入审核意见"同意发布"
		Driver.switchToFirstWindow();
		dialog.getCommentInput().input("同意发布");

		// 选择审核结论同意
		dialog.getAgreeRb().check();

		// 点击确定
		dialog.getConfirmBtn().click();
		Driver.wait(1000);

		// 验证提示信息：处理成功
		this.verifyAlert(taskPage, "处理成功");

		// 验证任务被移出未处理任务列表
		Assert.assertEquals(taskPage.getTaskTable().getRowCount(), rowCount - 1);

		// 验证交易场次状态变为"已发布"
		TradeFairPage tradeFairPage = taskPage.openTradeFairPage();
		HtmlRow row = tradeFairPage.getTradeFairTable().findRow(TradeFairPage.NameColNo, tradeFairData.Name);
		Assert.assertEquals(row.getCellValue(TradeFairPage.StatusColNo), "已发布");

		// 点击流转历史
		row.getCell(TradeFairPage.OpsColNo).findElementByLinkText("流转历史").click();
		Driver.wait(1000);

		// 验证流转历史中有两条记录，验证数据正确
		ViewHistoryDialog histDialog = new ViewHistoryDialog();
		FlowStepData[] expectedSteps = new FlowStepData[] {
				new FlowStepData(1, Constants.tradeTestName, new Date(), "", ""),
				new FlowStepData(2, Constants.tradeTLName, new Date(), "同意", "同意发布") };
		histDialog.verifyFlowStepData(expectedSteps);

		// 关闭流转历史
		histDialog.getCloseBtn().click();
		Driver.wait(1000);

		// 点击场次名称，验证打开的是查看页面
		tradeFairPage.openTradeFair(tradeFairData.Name);
		Assert.assertTrue(Driver.isUrlOpened(Constants.urlTtcm + "views/home.html#/numpre/", false),
				"提交后交易场次打开后显示的不是查看页面,当前页面：" + Driver.getCurrentUrl());
	}
}
