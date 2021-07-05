package com.hteis.webtest.testcases;

import java.util.ArrayList;

import org.testng.*;
import org.testng.annotations.Test;

import com.hteis.webtest.business.Company;
import com.hteis.webtest.business.User;
import com.hteis.webtest.common.*;
import com.hteis.webtest.entities.*;
import com.hteis.webtest.selenium.*;
import com.hteis.webtest.pages.*;

public class CompanyAuditTest extends TCBase {

	@Test(suiteName="Sep", groups="公司资料管理", priority=1, testName="企业信息提交审核",
			description= "企业信息填写时，所有必填信息已填时可提交审核\r\n"
					+"一个或多个必填信息未填时不可提交审核\r\n"
					+"验证提交后流程图状态变为信息审核中\r\n"
					+"提交后流程历史显示正确"	)
	public void Sep_CompanySubmit() throws Exception{
		//1. 注册一个新用户
		UserInfo buyer = User.registerNewBuyer();
		
		//2. 使用新用户登录到https://tcrm.hteis.cn/
		this.loginCust(buyer.loginName, buyer.password);
		PageBase homePage = new PageBase();
		homePage.openNavMenu("公司资料管理", "企业信息管理");
		CompanyInfoMgmtPage cmpyPage = new CompanyInfoMgmtPage();		
		
		//3. 点击信息未完善图标	
		cmpyPage.getIncompleteBtn().click();
		Driver.wait(1000);
		
		//4. 验证此时提交按钮不可用
		Assert.assertFalse(cmpyPage.getSubmitBtn().isEnabled(), "提交按钮此时不应该可点击");
		
		//5. 点击基础信息标签页，输入数据后保存
		cmpyPage.getMainInfoTab().click();
		CompanyMainInfo mainInfo = CompanyMainInfo.createLargeCmpyInfo();
		CompanyMainInfoPage mainInfoPage = new CompanyMainInfoPage();
		mainInfoPage.setFieldValues(mainInfo);
		cmpyPage.getSaveBtn().click();
		Driver.wait(1000);
		
		//6. 验证此时提交按钮不可用
		Assert.assertFalse(cmpyPage.getSubmitBtn().isEnabled(), "提交按钮此时不应该可点击");
		
		//7. 点击企业认证信息标签页，输入数据后保存
		cmpyPage.getEntInfoTab().click();
		CompanyRegInfo regInfo = CompanyRegInfo.createCompanyRegInfo();
		CompanyRegInfoPage regInfoPage = new CompanyRegInfoPage();
		regInfoPage.setFieldValues(regInfo);
		cmpyPage.getSaveBtn().click();
		Driver.wait(1000);	
		
		//8. 验证此时提交按钮不可用
		Assert.assertFalse(cmpyPage.getSubmitBtn().isEnabled(), "提交按钮此时不应该可点击");
		
		//9.点击账号信息标签页，输入数据后保存
		cmpyPage.getAccInfoTab().click();
		CompanyBankAccInfo accInfo = CompanyBankAccInfo.createBankData();
		CompanyAccInfoPage accPage = new CompanyAccInfoPage();
		accPage.setFieldValues(accInfo);
		cmpyPage.getSaveBtn().click();		
		Driver.wait(1000);	
		
		//10.验证此时提交按钮不可用
		Assert.assertFalse(cmpyPage.getSubmitBtn().isEnabled(), "提交按钮此时不应该可点击");
		
		//11.点击主要联系人信息标签页，输入数据后保存
		cmpyPage.getContactInfoTab().click();
		CompanyContactInfo contactInfo = CompanyContactInfo.create(); 
		CompanyContactPage contactPage = new CompanyContactPage();
		contactPage.setFieldValues(contactInfo);
		cmpyPage.getSaveBtn().click();
		Driver.wait(1000);
		
		//12.验证此时提交按钮不可用
		Assert.assertFalse(cmpyPage.getSubmitBtn().isEnabled(), "提交按钮此时不应该可点击");
		
		//13.点击用电信息标签页，创建用电信息后保存
		cmpyPage.getEleInfoTab().click();	
		cmpyPage.getAddEleInfoLink().click();
		Driver.wait(1000);
		CompanyEleInfo eleInfo1 = CompanyEleInfo.createLargeElectroData();
		CompanyEleInfoForm form1 = new CompanyEleInfoForm(cmpyPage.getEleInfoForms().get(0));
		form1.setFieldValues(eleInfo1);
		form1.getSaveBtn().click();
		Driver.wait(1000);
		
		//14.验证此时提交按钮可用，点击提交按钮
		Assert.assertTrue(cmpyPage.getSubmitBtn().isEnabled(), "提交按钮不可点击");
		cmpyPage.getSubmitBtn().click();
		Driver.wait(1000);
		
		//15.验证提示信息：提交成功
		this.verifyAlert(cmpyPage, "送审成功");
		Logger.logInfo(String.format("Company %s is submitted", mainInfo.CompanyName));		
		
		//16.点击查看流程历史
		cmpyPage.getViewHistoryBtn().click();
		
		//17.验证流程历史中只有一个步骤
		ViewHistoryDialog dialog = new ViewHistoryDialog();
		ArrayList<FlowStepData> steps = dialog.getFlowStepData();
		Assert.assertEquals(steps.size(), 1);
		
		//18.验证步骤数据正确;
		FlowStepData step = steps.get(0);
		Assert.assertEquals(step.SequenceNo, 1);
		Assert.assertEquals(step.Operator, buyer.loginName);
		Assert.assertEquals(DateUtil.getCNDateStr(step.CreateDate), DateUtil.getCNTodayStr());
		Assert.assertEquals(step.Conclusion, "");
		Assert.assertEquals(step.Comment, "");
		
		//19.点击基本信息标签页
		cmpyPage.getMainInfoTab().click();
		
		//20.点击关闭按钮
		cmpyPage.getCloseBtn().click();
		
		//21.验证流程图中信息审核中为激活状态，其他为不可用状态
		boolean[] expectedStatus = {false, false, false, false, true, true, false, false};
		Assert.assertEquals(cmpyPage.getBtnStatus(), expectedStatus, "填写企业认证信息后状态图中的按钮状态不正确");
		
		//22.点击信息审核中图标，验证页面跳转到详细信息页面
		cmpyPage.getReviewBtn().click();
		Assert.assertEquals(Driver.getCurrentUrl(), Constants.urlTcrm + "views/home.html#/salecustpre");
		
		//23.点击返回按钮
		CompanyDetailsPage detailPage = new CompanyDetailsPage();
		detailPage.getBackBtn().click();
		
		//24.验证流程图下方的流程步骤列表中只有一项
		steps = cmpyPage.getFlowStepData();
		
		//25.验证流程步骤中的数据正确
		step = steps.get(0);
		Assert.assertEquals(step.SequenceNo, 1);
		Assert.assertEquals(step.Operator, buyer.loginName);
		Assert.assertEquals(DateUtil.getCNDateStr(step.CreateDate), DateUtil.getCNTodayStr());
		Assert.assertEquals(step.Conclusion, "");
		Assert.assertEquals(step.Comment, "");
		
	}
	
	@Test(suiteName="Sep", groups="公司资料管理", priority=2, testName="客户查看审核流程-提交后",
			description= "客户提交公司资料审核后在已处理任务中可以查看任务信息\r\n"
						+"任务流程图正确")
	public void Sep_ViewTask01() throws Exception{
		
	}
	
	@Test(suiteName="Sep", groups="公司资料管理", priority=1, testName="审核用户资料-通过",
			description= "公司资料提交后，售电业务主管可在未处理任务中看到申请\r\n"
					+"任务处理对话框中可查看公司信息\r\n"
					+"验证查看原始单据功能\r\n"
					+"售电业务主管可通过审批\r\n"
					+"审批后任务移到已处理任务中")
	public void Sep_CompanyAudit01() throws Exception{
		//1. 创建一个新购电公司并提交审核
		CompanyData companyData = CompanyData.createCompanyData();
		Company.createBuyerCompany(companyData);				
		
		//2. 使用售电主管账号登录
		Driver.reLaunchBrowser();
		this.loginSep(Constants.saleTLUsr, Constants.saleTLPwd);
		
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
		Driver.wait(3000);
		
		//5. 在任务处理对话框中验证公司资料显示正确
		TaskDialog dialog = new TaskDialog();
		dialog.verifyCompanyData(companyData);
		
		//6. 点击查看原始单据，验证公司详细信息页面弹出
		dialog.getOrigDocLink().click();
		Driver.wait(5000);
		Assert.assertTrue(Driver.isUrlOpened(Constants.urlTcrm + "views/printTemplate.html#/salecustpre?id=", false), 
				"未打开公司详细信息页面");
		Driver.closeWindow();		
		
		//7. 填写审批意见"通过"，选择同意，点击确定
		Driver.switchToFirstWindow();
		dialog.getCommentInput().input("通过");
		dialog.getAgreeRb().check();
		dialog.getConfirmBtn().click();	
		Driver.wait(1000);
		
		//8. 验证任务从未处理任务表格中移除
		Assert.assertNull(taskTable.findRow(TaskActivePage.ApplicantColNo, companyData.Admin.loginName));
		
		//9. 打开已处理任务页面
		taskPage.openNavMenu("待处理工作", "已处理任务");	
		
		//10.验证任务被移到已处理任务中
		TaskCompletedPage compTaskPage = new TaskCompletedPage();
		compTaskPage.getPager().goLastPage();
		taskTable = compTaskPage.getTaskTable();
		Assert.assertNotNull(taskTable.findRow(TaskCompletedPage.ApplicantColNo, companyData.Admin.loginName));
	}
	
	@Test(suiteName="Sep", groups="公司资料管理", priority=2, testName="客户查看审核流程-通过后",
			description= "公司资料审核通过后在客户已处理任务中的任务信息正确\r\n"
						+"任务流程图正确\r\n"
						+"企业资料管理页面中的流程图状态正确"
						+"企业资料管理页面中的任务处理记录正确")
	public void Sep_ViewTask02() throws Exception{
		
	}
	
	
	
	
}
