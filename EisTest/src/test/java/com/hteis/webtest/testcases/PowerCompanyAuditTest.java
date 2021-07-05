package com.hteis.webtest.testcases;

import java.util.Date;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.hteis.webtest.business.PowerCompany;
import com.hteis.webtest.common.DateUtil;
import com.hteis.webtest.entities.*;
import com.hteis.webtest.pages.*;
import com.hteis.webtest.pages.cust.*;
import com.hteis.webtest.selenium.*;

public class PowerCompanyAuditTest extends TCBase {
	
	@Test(suiteName="Sep", groups="客户管理", priority=1, testName="提交发电企业信息",
			description="验证发电企业创建后可提交审核\r\n"
					+ "提交后状态变为审核中\r\n"
					+ "验证提交前后可用的操作正确\r\n"
					+ "提交后已处理任务中数据和流程图正确")
	public void Sep_SubmitPowerCompany() throws Exception{
		//使用客户主管账号登录
		this.loginCust(Constants.custTLUsr, Constants.custTLPwd);
		
		//创建一个发电企业
		PowerCompanyData data = PowerCompanyData.create();
		PowerCompany.create(data);
		
		//验证可用的操作有"编辑"、"提交"和"详情"
		PowerCompanyPage companyPage = new PowerCompanyPage();
		companyPage.verifyOperations(data.MainInfo.Name, new String[]{"编辑", "提交", "详情"});
		
		//点击提交按钮				
		Date submitTime = companyPage.submitCompany(data.MainInfo.Name);		
		
		//验证提示信息：送审成功
		companyPage.verifyAlert("送审成功");
		companyPage.closeAlert();
		
		//验证发电企业状态变为“信息审核中”
		data.Status = "信息审核中";
		companyPage.verifyData(data);				
		
		//验证可用的操作为"详情"
		companyPage.verifyOperations(data.MainInfo.Name, new String[]{"详情"});
		
		//打开已处理任务页面
		TaskCompletedPage taskPage = companyPage.openCompletedTaskPage();
		
		//在刚刚创建的任务中验证申请人、流程类型、申请时间正确
		HtmlRow row = taskPage.getAndVerifyTask(Constants.custTLName, "发电企业信息申请流程", submitTime);
		
		//点击查看按钮
		row.getCell(TaskCompletedPage.OpsColNo).findElementByLinkText("查看").click();
		Driver.wait(5000);
		
		//在对话框中查看发电企业信息正确
		TaskCompletedDialog dialog = new TaskCompletedDialog();
		dialog.verifyPowerCompanyData(data);
		
		//验证流程步骤列表只有一步
		dialog.verifyFlowStepData(new FlowStepData[]{new FlowStepData(1, Constants.custTLName, submitTime, "", "")});
		
		//点击关闭按钮
		dialog.getCloseBtn().click();
		
		//点击查看流程图，验证流程图状态正确
	}
	
	@Test(suiteName="Sep", groups="客户管理", priority=1, testName="审核发电企业信息-通过",
			description= "发电企业提交后，客户经理可在未处理任务中看到申请\r\n"
					+"任务处理对话框中可查看企业信息\r\n"
					+"验证查看原始单据功能\r\n"
					+"客户经理可通过审批\r\n"
					+"审批后任务移到已处理任务中"	)
	public void Sep_PowerCompanyAudit01() throws Exception{
		//使用客户主管账号登录
		this.loginCust(Constants.custTLUsr, Constants.custTLPwd);
		
		//创建一个发电企业
		PowerCompanyData data = PowerCompanyData.create();
		PowerCompany.create(data);
		
		//点击提交按钮	
		PowerCompanyPage companyPage = new PowerCompanyPage();
		Date submitTime = companyPage.submitCompany(data.MainInfo.Name);		
		
		//验证提示信息：送审成功
		companyPage.verifyAlert("送审成功");
		companyPage.closeAlert();
		
		//使用客户经理账号登录
		Driver.reLaunchBrowser();
		CustHomePage homePage = this.loginCust(Constants.custManagerUsr, Constants.custManagerPwd);
		
		//打开未处理任务页面，将表格翻到最后一页
		TaskActivePage activePage = homePage.openTaskPage();
		activePage.getPager().goLastPage();
		
		//找到刚刚生成的未处理任务，验证申请人和任务名称正确
		HtmlRow row = activePage.getAndVerifyTask(Constants.custTLName, "发电企业资料审核", submitTime);
		String submitTimeStr = row.getCellValue(TaskActivePage.ApplyTimeColNo);
		
		//点击处理任务
		row.getCell(TaskActivePage.OperationColNo).findElementByLinkText("处理任务").click();
		Driver.wait(3000);
		
		//在处理任务对话框中验证发电企业信息显示正确
		TaskDialog dialog = new TaskDialog();
		dialog.verifyPowerCompanyData(data);
		
		//点击查看原始单据，验证公司详细信息页面弹出
		dialog.getOrigDocLink().click();
		Driver.wait(3000);
		Assert.assertTrue(Driver.isUrlOpened(Constants.urlTcrm + "views/printTemplate.html#/pgepre?id=", false), 
				"未打开发电企业详细信息页面");
		Driver.closeWindow();		
		
		//填写审批意见"通过"，选择同意，点击确定
		Driver.switchToFirstWindow();
		dialog.getCommentInput().input("通过");
		dialog.getAgreeRb().check();
		dialog.getConfirmBtn().click();	
		Driver.wait(1000);
		activePage.verifyAlert("处理成功");
		activePage.closeAlert();
		
		//验证任务从未处理任务表格中移除
		Assert.assertNull(activePage.getTaskTable().findRow(TaskActivePage.ApplyTimeColNo, submitTimeStr), "任务处理后未从待处理任务表格中移除");
		
		//打开已处理任务页面
		activePage.openNavMenu("待处理工作", "已处理任务");	
		
		//验证任务被移到已处理任务中
		TaskCompletedPage taskPage = new TaskCompletedPage();
		Assert.assertNotNull(taskPage.getTaskTable().findRow(TaskCompletedPage.ApplyTimeColNo, submitTimeStr), "任务处理后未移到已处理任务中");
	}
	
	@Test(suiteName="Sep", groups="客户管理", priority=1, testName="审核发电企业信息-驳回",
			description= "发电企业提交后，客户经理可驳回申请\r\n"
					+"发电企业信息驳回后状态变回待审核\r\n"
					+"发电企业信息驳回后申请人可在未处理任务中看到\r\n"
					+"发电企业信息驳回后申请人可在已处理任务中看到并可下载附件\r\n"
					+"验证数据和流程图正确\r\n"
					+"发电企业信息驳回后可再次编辑提交")
	public void Sep_PowerCompanyAudit02() throws Exception{
		
	}
	
	@Test(suiteName="Sep", groups="客户管理", priority=1, testName="查看已审核通过的任务",
			description= "发电企业通过审核后，申请人可在已处理任务中看到\r\n"
					+ "验证任务流程步骤正确，流程图正确\r\n"
					+ "发电企业通过审核后不可编辑\r\n"
					+ "审批后发电企业状态变为审批完成")
	public void Sep_PowerCompanyAudit03() throws Exception{
		//使用客户主管登录并创建和提交一个发电企业，然后使用客户经理登录通过审批
		this.loginCust(Constants.custTLUsr, Constants.custTLPwd);
		PowerCompanyData data = PowerCompanyData.create();
		PowerCompany.createAndApprove(data);
		
		//再次使用客户主管账号登录
		Driver.reLaunchBrowser();
		CustHomePage homePage = this.loginCust(Constants.custTLUsr, Constants.custTLPwd);
		
		//打开已处理任务页面，找到刚刚创建的任务
		TaskCompletedPage taskPage = homePage.openCompletedTaskPage();
		HtmlRow row = taskPage.getTaskTable().findRow(TaskCompletedPage.ApplyTimeColNo, DateUtil.getCNDateTimeStr(data.SubmitTime));
		
		//点击查看打开查看任务对话框
		row.getCell(TaskCompletedPage.OpsColNo).findElementByLinkText("查看").click();
		Driver.wait(3000);
		
		//验证企业信息数据正确
		TaskCompletedDialog dialog = new TaskCompletedDialog();
		dialog.verifyPowerCompanyData(data);
		
		//验证流程图中的步骤包括两步：申请和审批通过，验证数据正确
		dialog.verifyFlowStepData(new FlowStepData[]{
				new FlowStepData(1, Constants.custTLName, data.SubmitTime, "", ""),
				new FlowStepData(2, Constants.custManagerName, data.ApproveTime, "同意", "通过")
		});
		
		//点击查看流程图,验证流程图状态正确	
		
		//关闭对话框
		dialog.getCloseBtn().click();
		
		//打开发电企业管理页面，验证新创建的发电企业可用的操作只有"详情"，企业状态变为“审批完成”	
		data.Status = "审批完成";
		PowerCompanyPage companyPage = taskPage.openPowerCompanyPage();
		companyPage.verifyData(data);
		companyPage.verifyOperations(data.MainInfo.Name, new String[]{"详情"});
	}
}
