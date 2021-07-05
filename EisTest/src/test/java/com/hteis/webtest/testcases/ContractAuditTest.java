package com.hteis.webtest.testcases;

import java.util.ArrayList;
import java.util.Date;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.hteis.webtest.business.*;
import com.hteis.webtest.common.*;
import com.hteis.webtest.entities.*;
import com.hteis.webtest.pages.*;
import com.hteis.webtest.selenium.*;

public class ContractAuditTest extends TCBase {

	@Test(groups="Sep", priority=1, testName="提交合同",
			description="验证合同创建后可提交审核\r\n"
					+ "提交后合同状态变为审核中\r\n"
					+ "提交后合同无法更改\r\n"
					+ "提交后流转历史里的数据正确")
	public void Sep_SubmitContract() throws Exception{
		//创建一个新购电公司
		CompanyData companyData = CompanyData.createCompanyData();
		Company.createAndApproveBuyerCompany(companyData);
		
		//使用srsale/srsale登录，为新公司创建一个合同
		Driver.reLaunchBrowser();
		this.loginSep(Constants.saleTestUsr, Constants.saleTestPwd);

		//为新公司创建一个新合同
		ContractData contractData = ContractData.createData(companyData.MainInfo.CompanyName, DateUtil.getThisYear(), "大客户套餐");
		Contract.create(contractData);
		
		//在合同列表中点击新创建的合同的编号
		ContractListPage listPage = new ContractListPage();
		listPage.getContractTable()
			.findRow(ContractListPage.CustNameColNo, companyData.MainInfo.CompanyName)
			.getCell(ContractListPage.ContractIdColNo)
			.findElementByTag("a")
			.click();
		
		//在编辑合同页面中点击提交按钮
		ContractNewPage newPage = new ContractNewPage();
		newPage.getSubmitBtn().click();
		//Driver.wait(1000);
		
		//验证提示消息"提交成功" 
		newPage.verifyAlert("提交成功");
		
		//点击返回按钮
		newPage.getBackBtn().click();		
		
		//验证合同状态变为"审核中"
		HtmlRow row = listPage.getContractTable().findRow(ContractListPage.CustNameColNo, companyData.MainInfo.CompanyName);
		String status = row.getCell(ContractListPage.StatusColNo).getText();
		Assert.assertEquals("审核中", status);
		
		//点击合同编号打开编辑页面
		row.getCell(ContractListPage.ContractIdColNo).findElementByTag("a").click();
		
		//验证：页面中只有返回按钮
		ArrayList<HtmlElement> buttons = newPage.findElementsByCss(".modal-footer button");
		Assert.assertEquals(buttons.size(), 1);
		Assert.assertEquals(buttons.get(0).getText(), "返回");
		
		//点击返回关闭编辑按钮
		buttons.get(0).click();
		
		//点击流转历史
		listPage.getContractTable()
			.findRow(ContractListPage.CustNameColNo, companyData.MainInfo.CompanyName)
			.getCell(ContractListPage.OpsColNo)
			.findElementByLinkText("流转历史")
			.click();
		
		//验证流转历史中只有一条记录，处理人和处理时间正确
		ViewHistoryDialog dialog = new ViewHistoryDialog();
		FlowStepData[] expectedSteps = new FlowStepData[]{new FlowStepData(1, Constants.saleTestName, new Date(), "", "")};
		dialog.verifyFlowStepData(expectedSteps);
	}	
	
	@Test(groups="Sep", priority=1, testName="审核合同-通过",
			description="合同提交后业务主管未处理任务中可看到申请\r\n"
					+ "验证处理任务对话框中数据正确\r\n"
					+ "审核后任务被移出未处理任务\r\n"
					+ "审核通过后合同状态为客户确认中\r\n"
					+ "审核通过后合同不可变更\r\n"
					+ "审核通过后流转历史里的数据正确")
	public void Sep_ContractAudit() throws Exception{
		//创建一个新购电公司
		CompanyData companyData = CompanyData.createCompanyData();
		Company.createAndApproveBuyerCompany(companyData);
		
		//为新购电公司创建一个合同并提交
		Driver.reLaunchBrowser();
		this.loginSep(Constants.saleTestUsr, Constants.saleTestPwd);
		ContractData contractData = ContractData.createData(companyData.MainInfo.CompanyName, DateUtil.getThisYear(), "大客户套餐");
		Contract.createAndSubmit(contractData);
		
		//使用售电主管srsalelead登录
		Driver.reLaunchBrowser();
		SepHomePage homePage = this.loginSep(Constants.saleTLUsr, Constants.saleTLPwd);
		
		//打开未处理任务页面
		homePage.openTaskPage();
		Driver.wait(2000);
		
		//找到最后一条任务，点击处理任务
		TaskActivePage taskPage = new TaskActivePage();
		HtmlTable taskTable = taskPage.getTaskTable();
		HtmlNgPager pager = taskPage.getPager();
		pager.goLastPage();
		int rowCount = taskTable.getRowCount();
		taskTable.getLastRow().getCell(TaskActivePage.OperationColNo).findElementByLinkText("处理任务").click();
		Driver.wait(3000);
		
		//验证合同数据正确
		TaskDialog dialog = new TaskDialog();
		dialog.verifyContractData(contractData);
		
		//点击查看原始单据，验证合同详细信息页面弹出
		dialog.getOrigDocLink().click();
		Driver.wait(3000);
		Assert.assertTrue(Driver.isUrlOpened(Constants.urlTsep + "views/Print.html#/conpre/", false), 
				"未打开合同详细信息页面");
		Driver.closeWindow();
		
		//输入审核意见"同意发布"
		Driver.switchToFirstWindow();
		dialog.getCommentInput().input("同意发布");
		
		//选择审核结论同意 
		dialog.getAgreeRb().check();
		
		//点击确定
		dialog.getConfirmBtn().click();
		Driver.wait(1000);
		
		//验证提示信息：处理成功
		this.verifyAlert(taskPage, "处理成功");
		
		//验证任务被移出未处理任务列表
		Assert.assertEquals(taskPage.getTaskTable().getRowCount(), rowCount - 1);
		
		//验证合同状态变为"客户确认中"
		taskPage.openContractListPage();
		ContractListPage contractPage = new ContractListPage();
		HtmlRow row = contractPage.getContractTable().findRow(ContractListPage.CustNameColNo, contractData.CustName);
		Assert.assertEquals(row.getCellValue(ContractListPage.StatusColNo), "客户确认中");
		
		//点击合同编号打开编辑页面
		row.getCell(ContractListPage.ContractIdColNo).findElementByTag("a").click();
		
		//验证：页面中只有返回按钮，点击返回按钮
		ContractNewPage newPage = new ContractNewPage();
		ArrayList<HtmlElement> buttons = newPage.findElementsByCss(".modal-footer button");
		Assert.assertEquals(buttons.size(), 1);
		Assert.assertEquals(buttons.get(0).getText(), "返回");	
		buttons.get(0).click();
		
		//点击流转历史，验证流转历史记录正确
		row = contractPage.getContractTable().findRow(ContractListPage.CustNameColNo, contractData.CustName);
		row.getCell(ContractListPage.OpsColNo).findElementByLinkText("流转历史").click();
		ViewHistoryDialog histDialog = new ViewHistoryDialog();
		
		FlowStepData[] expectedSteps = new FlowStepData[]{
				new FlowStepData(1, Constants.saleTestName, new Date(), "", ""),
				new FlowStepData(2, Constants.saleTLName, new Date(), "同意", "同意发布")};
		histDialog.verifyFlowStepData(expectedSteps);
	}
	
	@Test(groups="Sep", priority=1, testName="确认合同-通过",
			description="合同审核通过后客户可在合同查询页面看到\r\n"
					+ "客户可在未处理任务中处理合同\r\n"
					+ "确认后任务被移出未处理任务列表\r\n"
					+ "合同确认后状态变为生效")
	public void Sep_ContractConfirm() throws Exception{
		//创建一个新购电公司
		CompanyData companyData = CompanyData.createCompanyData();
		Company.createAndApproveBuyerCompany(companyData);
		
		//为新购电公司创建一个合同并提交
		Driver.reLaunchBrowser();
		this.loginSep(Constants.saleTestUsr, Constants.saleTestPwd);
		ContractData contractData = ContractData.createData(companyData.MainInfo.CompanyName, DateUtil.getThisYear(), "大客户套餐");
		Contract.createAndSubmit(contractData);
		
		//使用售电主管srsalelead登录
		Driver.reLaunchBrowser();
		this.loginSep(Constants.saleTLUsr, Constants.saleTLPwd);
		Contract.Approve();
		
		//使用购电用户登录
		Driver.reLaunchBrowser();
		ShopHomePage homePage = this.LoginShop(companyData.Admin.loginName, companyData.Admin.password);
		homePage.openCustContractPage();
		
		//验证：合同列表中有一条记录，状态为"客户确认中"，验证其他数据正确
		CustContractPage contractPage = new CustContractPage();
		HtmlRow row = contractPage.getContractTable().getFirstRow();
		Assert.assertEquals(row.getCellValue(CustContractPage.ContractNameColNo), contractData.ContractName, "合同名称不对");
		Assert.assertEquals(row.getCellValue(CustContractPage.SignDateColNo), DateUtil.getCNDateStr(contractData.SignDate), "合同签订日期不对");
		Assert.assertEquals(row.getCellValue(CustContractPage.ContractStartDateColNo), DateUtil.getCNDateStr(contractData.ContractStartDate), "合同开始日期不对");
		Assert.assertEquals(row.getCellValue(CustContractPage.ContractEndDateColNo), DateUtil.getCNDateStr(contractData.ContractEndDate), "合同结束日期不对");
		
		//打开未处理任务页面，验证客户确认任务数据正确
		contractPage.openTaskPage();
		TaskActivePage taskPage = new TaskActivePage();
		row = taskPage.getTaskTable().getFirstRow();
		Assert.assertEquals(row.getCellValue(TaskActivePage.ApplicantColNo), Constants.saleTestName, "申请人不对");
		Assert.assertEquals(row.getCellValue(TaskActivePage.TaskNameColNo), "客户确认", "任务名称不对");
		Assert.assertEquals(row.getCellValue(TaskActivePage.ApplyTimeColNo).substring(0, 10), DateUtil.getCNTodayStr(), "申请时间不对");
		
		//点击处理任务
		row.getCell(TaskActivePage.OperationColNo).findElementByLinkText("处理任务").click();
		Driver.wait(3000);
		
		//验证处理任务对话框中的合同内容正确
		TaskDialog dialog = new TaskDialog();
		dialog.verifyContractData(contractData);
		
		//点击查看原始单据，验证合同详细信息页面弹出
		dialog.getOrigDocLink().click();
		Driver.wait(3000);
		Assert.assertTrue(Driver.isUrlOpened(Constants.urlTsep + "views/Print.html#/conpre/", false), "未打开合同详细信息页面");
		Driver.closeWindow();
		
		//输入审核意见"同意发布"
		Driver.switchToFirstWindow();
		dialog.getCommentInput().input("确认无误");
		
		//选择审核结论同意 
		dialog.getAgreeRb().check();
		
		//点击确定
		dialog.getConfirmBtn().click();
		Driver.wait(1000);
		
		//验证提示信息：处理成功
		this.verifyAlert(taskPage, "处理成功");
		
		//验证任务被移出未处理任务列表
		Assert.assertEquals(taskPage.getTaskTable().getRowCount(), 0);
		
		//打开合同查询页面，验证合同状态变为生效
		taskPage.openCustContractPage();
		Driver.wait(1000);
		Assert.assertEquals(taskPage.getTaskTable().getFirstRow().getCellValue(CustContractPage.StatusColNo), "生效", "客户确认合同后合同状态不对");
	}
	
	@Test(groups="Sep", priority=1, testName="查看已处理合同",
			description="售电主管同意合同后可在已处理任务中查看任务信息，验证此时流程图和流程步骤正确\r\n"
					+ "客户确认合同后可在已处理任务中查看任务信息，验证此时流程图和流程步骤正确\r\n")						
	public void Sep_ViewContractTask() throws Exception{
		//创建一个新购电公司
		CompanyData companyData = CompanyData.createCompanyData();
		Company.createAndApproveBuyerCompany(companyData);
		
		//为新购电公司创建一个合同并提交
		Driver.reLaunchBrowser();
		this.loginSep(Constants.saleTestUsr, Constants.saleTestPwd);
		ContractData contractData = ContractData.createData(companyData.MainInfo.CompanyName, DateUtil.getThisYear(), "大客户套餐");
		Contract.createAndSubmit(contractData);
		
		//使用售电主管srsalelead登录
		Driver.reLaunchBrowser();
		this.loginSep(Constants.saleTLUsr, Constants.saleTLPwd);
		Contract.Approve();
		
		//打开已处理任务页面
		TaskCompletedPage taskPage = new PageBase().openCompletedTaskPage();
		
		//验证已处理任务中的数据
		HtmlRow row = taskPage.getTaskTable().getFirstRow();
		Assert.assertEquals(row.getCellValue(TaskCompletedPage.ApplicantColNo), "银守殿", "申请人错误");
		Assert.assertEquals(row.getCellValue(TaskCompletedPage.FlowTypeColNo), "售电合同审核流程", "流程类型错误");
		Assert.assertEquals(row.getCellValue(TaskCompletedPage.ApplyTimeColNo).substring(0, 10), DateUtil.getCNDateStr(new Date()), "申请时间错误");
		
		//点击查看
		row.getCell(TaskCompletedPage.OpsColNo).findElementByLinkText("查看").click();
		Driver.wait(3000);
		
		//验证合同内容
		TaskCompletedDialog dialog = new TaskCompletedDialog();
		dialog.verifyContractData(contractData);		
		
		//验证下面的流程步骤正确
		FlowStepData[] expectedSteps = new FlowStepData[]{ 
				new FlowStepData(1, Constants.saleTestName, new Date(), "", "" ),
				new FlowStepData(2, Constants.saleTLName, new Date(), "同意", "同意发布")};
		dialog.verifyFlowStepData(expectedSteps);
		
		//点击关闭
		dialog.getCloseBtn().click();
	
		//使用购电用户登录
		Driver.reLaunchBrowser();
		ShopHomePage homePage = this.LoginShop(companyData.Admin.loginName, companyData.Admin.password);
		homePage.openCustContractPage();
		
		//打开未处理任务页面
	    TaskActivePage activeTaskPage = homePage.openTaskPage();
		row = activeTaskPage.getTaskTable().getFirstRow();
		
		//点击处理任务
		row.getCell(TaskActivePage.OperationColNo).findElementByLinkText("处理任务").click();
		Driver.wait(3000);
		TaskDialog taskDialog = new TaskDialog();
		
		//输入审核意见"确认无误"
		taskDialog.getCommentInput().input("确认无误");
		
		//选择审核结论同意 
		taskDialog.getAgreeRb().check();
		
		//点击确定
		taskDialog.getConfirmBtn().click();
		Driver.wait(1000);
		
		//验证提示信息：处理成功
		this.verifyAlert(activeTaskPage, "处理成功");
		
		//打开已处理任务页面
		taskPage = activeTaskPage.openCompletedTaskPage();
		Driver.wait(1000);
		
		//验证已处理任务中的数据正确
		row = taskPage.getTaskTable().getFirstRow();
		Assert.assertEquals(row.getCellValue(TaskCompletedPage.ApplicantColNo), "银守殿", "申请人错误");
		Assert.assertEquals(row.getCellValue(TaskCompletedPage.FlowTypeColNo), "售电合同审核流程", "流程类型错误");
		Assert.assertEquals(row.getCellValue(TaskCompletedPage.ApplyTimeColNo).substring(0, 10), DateUtil.getCNDateStr(new Date()), "申请时间错误");
		
		//点击查看
		row.getCell(TaskCompletedPage.OpsColNo).findElementByLinkText("查看").click();
		Driver.wait(3000);
		
		//验证合同内容
		dialog = new TaskCompletedDialog();
		dialog.verifyContractData(contractData);		
		
		//验证下面的流程步骤正确
		expectedSteps = new FlowStepData[]{ 
				new FlowStepData(1, Constants.saleTestName, new Date(), "", "" ),
				new FlowStepData(2, Constants.saleTLName, new Date(), "同意", "同意发布"),
				new FlowStepData(3, companyData.ContactInfo.Name, new Date(), "同意", "确认无误")};
		dialog.verifyFlowStepData(expectedSteps);
		
		//点击查看流程图，验证流程图正确
		
		//点击关闭
		dialog.getCloseBtn().click();
	}
	
	
}
