package com.hteis.webtest.testcases;

import org.testng.annotations.Test;

import java.util.Date;

import org.testng.*;
import com.hteis.webtest.business.CustService;
import com.hteis.webtest.common.DateUtil;
import com.hteis.webtest.entities.Constants;
import com.hteis.webtest.entities.CustServiceData;
import com.hteis.webtest.pages.cust.*;
import com.hteis.webtest.selenium.*;

public class CustServiceTest extends TCBase {

	@Test(suiteName = "Sep", groups = "客户管理", priority = 1, testName = "创建客户服务记录-成功", 
			description = "验证数据有效时客户服务可创建成功\r\n"
					+ "当用户类型为注册用户时，单位名称只能选择购电公司\r\n“" 
					+ "当用户类型未非注册用户时，单位名称可任意输入\r\n" 
					+ "客户服务详情页面的客户服务数据显示正确")
	public void CustServiceNew01() {
		// 使用客户专员账号登录系统
		CustHomePage homePage = this.loginCust(Constants.custTestUsr, Constants.custTestPwd);

		// 打开客户服务管理页面
		CustServicePage custPage = homePage.openCustServicePage();

		// 点击创建服务请求按钮
		custPage.getNewBtn().click();

		// 设置用户类型为注册用户，输入有效数据
		CustServiceNewDialog dialog = new CustServiceNewDialog();
		CustServiceData data = CustServiceData.createRegCustData();
		dialog.setFieldValues(data);

		// 点击保存按钮
		dialog.getSaveBtn().click();
		Driver.wait(1000);

		// 验证提示消息：保存成功
		custPage.verifyAlert("保存成功");
		custPage.closeAlert();
		// Driver.wait(1000);

		// 验证新建的客户服务记录出现在表中，验证数据正确
		custPage.verifyData(data);

		// 点击详情
		custPage.getCustServiceTable().findRow(CustServicePage.DescriptionColNo, data.Description)
				.getCell(CustServicePage.OpsColNo).findElementByLinkText("详情").click();
		Driver.wait(1000);

		// 验证服务记录详情对话框中的数据正确
		CustServiceDetailsDialog detailDialog = new CustServiceDetailsDialog();
		detailDialog.verifyData(data);
		detailDialog.getCloseBtn().click();
		Driver.wait(1000);

		// 再次创建一个服务记录，使用非注册用户
		custPage.getNewBtn().click();
		CustServiceData data2 = CustServiceData.createNonRegCustData();
		dialog.setFieldValues(data2);
		dialog.getSaveBtn().click();
		Driver.wait(1000);
		custPage.verifyAlert("保存成功");
		custPage.closeAlert();

		// 在服务记录表中验证数据
		custPage.verifyData(data2);

		// 点击详情，验证服务记录详情对话框中的数据正确
		custPage.getCustServiceTable().findRow(CustServicePage.DescriptionColNo, data2.Description)
				.getCell(CustServicePage.OpsColNo).findElementByLinkText("详情").click();
		Driver.wait(1000);
		detailDialog.verifyData(data2);
	}

	@Test(suiteName = "Sep", groups = "客户管理", priority = 3, testName = "创建客户服务记录-失败", description = "字段数据验证及错误提示")
	public void CustServiceNew02() {

	}

	@Test(suiteName = "Sep", groups = "客户管理", priority = 2, testName = "编辑客户服务记录", description = "验证客户服务状态为处理中时可编辑\r\n"
			+ "验证客户服务状态为处理完成时没有编辑操作")
	public void CustServiceEdit() {
		// 使用客户专员账号登录系统
		this.loginCust(Constants.custTestUsr, Constants.custTestPwd);

		// 创建一个注册用户的服务记录
		CustServiceData data = CustServiceData.createRegCustData();
		CustService.create(data);

		// 点击编辑
		CustServicePage custPage = new CustServicePage();
		custPage.getCustServiceTable().findRow(CustServicePage.DescriptionColNo, data.Description)
				.getCell(CustServicePage.OpsColNo).findElementByLinkText("编辑").click();

		// 将用户类型改为非注册用户，并修改其他数据
		CustServiceData.updateRegCustData(data);
		CustServiceEditDialog editDialog = new CustServiceEditDialog();
		editDialog.setFieldValues(data);

		// 点击保存按钮
		editDialog.getSaveBtn().click();
		Driver.wait(1000);

		// 验证提示消息：保存成功
		custPage.verifyAlert("保存成功");
		custPage.closeAlert();
		Driver.wait(1000);

		// 在服务记录表中验证数据
		custPage.verifyData(data);

		// 创建一个注册用户的服务记录
		CustServiceData data2 = CustServiceData.createRegCustData();
		CustService.create(data2);

		// 点击编辑
		custPage = new CustServicePage();
		custPage.getCustServiceTable().findRow(CustServicePage.DescriptionColNo, data2.Description)
				.getCell(CustServicePage.OpsColNo).findElementByLinkText("编辑").click();

		// 将用户类型改为非注册用户，并修改其他数据
		CustServiceData.updateNonRegCustData(data2);
		editDialog = new CustServiceEditDialog();
		editDialog.setFieldValues(data2);

		// 点击保存按钮
		editDialog.getSaveBtn().click();
		Driver.wait(1000);

		// 验证提示消息：保存成功
		custPage.verifyAlert("保存成功");
		custPage.closeAlert();
		Driver.wait(1000);

		// 在服务记录表中验证数据
		custPage.verifyData(data2);
	}

	@Test(suiteName = "Sep", groups = "客户管理", priority = 2, testName = "查询客户服务记录", 
			description = "验证客户服务记录可按单位名称/记录时间/用户类型/处理时间/问题类型/处理状态/客户名称查询\r\n"
					+ "验证重置功能可清除所有查询条件并显示所有服务请求\r\n" 
					+ "验证没有设置任何搜索条件时查询将显示所有服务请求")
	public void CustServiceSearch() throws Exception {
		
		int loadTime = 2000;

		// 使用客户专员账号登录系统
		CustHomePage homePage = this.loginCust(Constants.custTestUsr, Constants.custTestPwd);

		// 打开客户服务管理页面
		CustServicePage custPage = homePage.openCustServicePage();
		Driver.wait(loadTime);
		HtmlNgPager pager = custPage.getPager();
		int count = pager.getTotalCount();

		// 使用单位名称搜索：单位名称包含"绿叶", 验证搜索结果正确
		String cmpy = "绿叶";
		custPage.getCompanyInput().setText(cmpy);
		custPage.getQueryBtn().click();
		Driver.wait(loadTime);
		
		HtmlRow result = custPage.getCustServiceTable().VerifyAllRows(custPage.getPager(), new ICheckRow() {
			public boolean CheckRow(HtmlRow row) {
				return row.getCellValue(CustServicePage.CompanyColNo).contains(cmpy);
			}
		});
		Assert.assertNull(result, "按单位名称的搜索结果不对，不符合条件的行：" + custPage.printData(result));
		
		
		final Date startTime = DateUtil.getDateTimeFromString("2017-03-15 00:00:00");
		final Date endTime = DateUtil.getDateTimeFromString("2017-03-16 23:59:59");
		
		// 使用记录时间搜索： >= 2017-03-16, 验证搜索结果正确
		custPage.getCompanyInput().clear();		
		custPage.getCreateStartTimePicker().setText(DateUtil.getCNDateStr(startTime));
		custPage.getQueryBtn().click();
		Driver.wait(loadTime);
		
		result = custPage.getCustServiceTable().VerifyAllRows(custPage.getPager(), new ICheckRow() {
			public boolean CheckRow(HtmlRow row) throws Exception {
				Date createDate = DateUtil.getDateTimeFromString(row.getCellValue(CustServicePage.CreateDateTimeColNo));
				return createDate.getTime() >= startTime.getTime();
			}
		});
		Assert.assertNull(result, "按记录时间段开始时间搜索结果不对，不符合条件的行：" + custPage.printData(result));
		custPage.getCreateStartTimePicker().clear();

		// 使用记录时间搜索：<=2017-03-20, 验证搜索结果正确
		custPage.getCreateEndTimePicker().setText(DateUtil.getCNDateStr(endTime));
		custPage.getQueryBtn().click();
		Driver.wait(loadTime);
		
		result = custPage.getCustServiceTable().VerifyAllRows(custPage.getPager(), new ICheckRow() {
			public boolean CheckRow(HtmlRow row) throws Exception {
				Date createDate = DateUtil.getDateTimeFromString(row.getCellValue(CustServicePage.CreateDateTimeColNo));
				return createDate.getTime() <= endTime.getTime();
			}
		});
		Assert.assertNull(result, "按记录时间段结束时间搜索结果不对，不符合条件的行：" + custPage.printData(result));
		
		// 按记录时间搜索:  2017-03-20>=记录时间 >= 2017-03-16
		custPage.getCreateStartTimePicker().setText(DateUtil.getCNDateStr(startTime));
		custPage.getQueryBtn().click();
		Driver.wait(loadTime);
		
		result = custPage.getCustServiceTable().VerifyAllRows(custPage.getPager(), new ICheckRow() {
			public boolean CheckRow(HtmlRow row) throws Exception {
				Date createDate = DateUtil.getDateTimeFromString(row.getCellValue(CustServicePage.CreateDateTimeColNo));
				return createDate.getTime() <= endTime.getTime() && createDate.getTime() >= startTime.getTime();
			}
		});
		Assert.assertNull(result, "按记录时间段开始时间和结束时间搜索结果不对，不符合条件的行：" + custPage.printData(result));
		custPage.getCreateStartTimePicker().clear();
		custPage.getCreateEndTimePicker().clear();
		
		// 按用户类型搜索: 用户类型=非注册用户，验证搜索结果正确
		String usrType = "非注册用户";		
		custPage.getUserTypeSelect().selectByText(usrType);
		custPage.getQueryBtn().click();
		Driver.wait(loadTime);
		
		result = custPage.getCustServiceTable().VerifyAllRows(custPage.getPager(), new ICheckRow() {
			public boolean CheckRow(HtmlRow row) throws Exception {
				return row.getCellValue(CustServicePage.UserTypeColNo).equals(usrType);
			}
		});
		Assert.assertNull(result, "按用户类型搜索结果不对，不符合条件的行：" + custPage.printData(result));
		custPage.getUserTypeSelect().selectByIndex(0);		
		
		
		// 使用处理时间搜索： >= 2017-03-16, 验证搜索结果正确
		custPage.getProcessStartTimePicker().setText(DateUtil.getCNDateStr(startTime));
		custPage.getQueryBtn().click();
		Driver.wait(loadTime);
		
		result = custPage.getCustServiceTable().VerifyAllRows(custPage.getPager(), new ICheckRow() {
			public boolean CheckRow(HtmlRow row) throws Exception {
				Date processDate = DateUtil.getDateTimeFromString(row.getCellValue(CustServicePage.ProcessDateTimeColNo));
				return processDate.getTime() >= startTime.getTime();
			}
		});
		Assert.assertNull(result, "按处理时间段开始时间搜索结果不对，不符合条件的行：" + custPage.printData(result));

		// 使用处理时间搜索：<=2017-03-20, 验证搜索结果正确
		custPage.getProcessStartTimePicker().clear();
		custPage.getProcessEndTimePicker().setText(DateUtil.getCNDateStr(endTime));
		custPage.getQueryBtn().click();
		Driver.wait(loadTime);
		
		result = custPage.getCustServiceTable().VerifyAllRows(custPage.getPager(), new ICheckRow() {
			public boolean CheckRow(HtmlRow row) throws Exception {
				Date processDate = DateUtil.getDateTimeFromString(row.getCellValue(CustServicePage.ProcessDateTimeColNo));
				return processDate.getTime() <= endTime.getTime();
			}
		});
		Assert.assertNull(result, "按处理时间段结束时间搜索结果不对，不符合条件的行：" + custPage.printData(result));
		
		// 按处理时间搜索:  2017-03-20>=记录时间 >= 2017-03-16
		custPage.getProcessStartTimePicker().setText(DateUtil.getCNDateStr(startTime));
		custPage.getQueryBtn().click();
		Driver.wait(loadTime);
		
		result = custPage.getCustServiceTable().VerifyAllRows(custPage.getPager(), new ICheckRow() {
			public boolean CheckRow(HtmlRow row) throws Exception {
				Date processDate = DateUtil.getDateTimeFromString(row.getCellValue(CustServicePage.ProcessDateTimeColNo));
				return processDate.getTime() <= endTime.getTime() && processDate.getTime() >= startTime.getTime();
			}
		});
		Assert.assertNull(result, "按处理时间段开始时间和结束时间搜索结果不对，不符合条件的行：" + custPage.printData(result));
		custPage.getProcessEndTimePicker().clear();
		custPage.getProcessStartTimePicker().clear();
		
		// 按问题类型搜索：问题类型=合同问题
		String issueType = "合同问题";
		custPage.getIssueTypeSelect().selectByText(issueType);
		custPage.getQueryBtn().click();
		Driver.wait(loadTime);
		
		result = custPage.getCustServiceTable().VerifyAllRows(custPage.getPager(), new ICheckRow() {
			public boolean CheckRow(HtmlRow row) throws Exception {
				return row.getCellValue(CustServicePage.IssueTypeColNo).equals(issueType);
			}
		});
		Assert.assertNull(result, "按问题类型搜索结果不对，不符合条件的行：" + custPage.printData(result));
		custPage.getIssueTypeSelect().selectByIndex(0);
		
		// 按处理状态搜索：处理状态=处理中
		String status = "处理中";
		custPage.getStatusSelect().selectByText(status);
		custPage.getQueryBtn().click();
		Driver.wait(loadTime);
		
		result = custPage.getCustServiceTable().VerifyAllRows(custPage.getPager(), new ICheckRow() {
			public boolean CheckRow(HtmlRow row) throws Exception {
				return row.getCellValue(CustServicePage.StatusColNo).equals(status);
			}
		});
		Assert.assertNull(result, "按处理状态搜索结果不对，不符合条件的行：" + custPage.printData(result));
		custPage.getStatusSelect().selectByIndex(0);
		
		// 按客户名称搜索：客户名称包含“小宝“
		String custName = "小宝";
		custPage.getCustNameInput().setText(custName);
		custPage.getQueryBtn().click();
		Driver.wait(loadTime);
		
		result = custPage.getCustServiceTable().VerifyAllRows(custPage.getPager(), new ICheckRow() {
			public boolean CheckRow(HtmlRow row) throws Exception {
				return row.getCellValue(CustServicePage.CustNameColNo).contains(custName);
			}
		});
		Assert.assertNull(result, "按客户名称搜索结果不对，不符合条件的行：" + custPage.printData(result));
		custPage.getCustNameInput().clear();
		
		// 使用所有条件查询：单位名称(四川)， 用户类型：注册用户，问题类型：账号问题，客户名称小宝，状态处理中，记录时间2017-03-15~2017-03-16
		custPage.getCompanyInput().setText("四川");
		custPage.getUserTypeSelect().selectByText("注册用户");
		custPage.getIssueTypeSelect().selectByText("账号问题");
		custPage.getCustNameInput().setText("小宝");
		custPage.getStatusSelect().selectByText("处理中");
		final Date startTime2 = DateUtil.getDateTimeFromString("2017-03-15 00:00:00");
		final Date endTime2 = DateUtil.getDateTimeFromString("2017-03-16 23:59:59");
		custPage.getCreateStartTimePicker().setText(DateUtil.getCNDateStr(startTime));
		custPage.getCreateEndTimePicker().setText(DateUtil.getCNDateStr(endTime));
		custPage.getProcessStartTimePicker().setText(DateUtil.getCNDateStr(startTime));
		custPage.getProcessEndTimePicker().setText(DateUtil.getCNDateStr(endTime));
		custPage.getQueryBtn().click();
		Driver.wait(loadTime);
		
		result = custPage.getCustServiceTable().VerifyAllRows(custPage.getPager(), new ICheckRow() {
			public boolean CheckRow(HtmlRow row) throws Exception {
				Date createDate = DateUtil.getDateTimeFromString(row.getCellValue(CustServicePage.CreateDateTimeColNo));
				Date processDate = DateUtil.getDateTimeFromString(row.getCellValue(CustServicePage.ProcessDateTimeColNo));
				return row.getCellValue(CustServicePage.CustNameColNo).contains("小宝")
					&& row.getCellValue(CustServicePage.CompanyColNo).contains("四川")
					&& row.getCellValue(CustServicePage.UserTypeColNo).equals("注册用户")
					&& row.getCellValue(CustServicePage.IssueTypeColNo).equals("账号问题")
					&& row.getCellValue(CustServicePage.StatusColNo).equals("处理中")
					&& processDate.getTime() <= endTime2.getTime()
					&& processDate.getTime() >= startTime2.getTime()
					&& createDate.getTime() <= endTime2.getTime()
					&& createDate.getTime() >= startTime2.getTime();
			}
		});
		Assert.assertNull(result, "按复合搜索结果不对，不符合条件的行：" + custPage.printData(result));
		
		// 点击重置按钮
		custPage.getResetBtn().click();
		Driver.wait(loadTime);
		
		// 验证搜索条件北重置
		Assert.assertEquals(custPage.getCompanyInput().getText(), "", "单位名称未重置");
		Assert.assertEquals(custPage.getUserTypeSelect().getSelectedIndex(), 0, "用户类型未重置");
		Assert.assertEquals(custPage.getIssueTypeSelect().getSelectedIndex(), 0, "问题类型未重置");
		Assert.assertEquals(custPage.getCustNameInput().getText(), "", "客户名称未重置");
		Assert.assertEquals(custPage.getCreateStartTimePicker().getText(), "", "记录时间段开始时间未重置");
		Assert.assertEquals(custPage.getCreateEndTimePicker().getText(), "", "记录时间段结束时间未重置");
		Assert.assertEquals(custPage.getProcessStartTimePicker().getText(), "", "处理时间段开始时间未重置");
		Assert.assertEquals(custPage.getProcessEndTimePicker().getText(), "", "处理时间段结束时间未重置");
		
		// 验证表中显示了所有记录
		Assert.assertEquals(custPage.getPager().getTotalCount(), count, "重置搜索条件后记录数不对");	
		
		//点击查询，验证表中显示了所有记录
		custPage.getQueryBtn().click();
		Driver.wait(loadTime);
		Assert.assertEquals(custPage.getPager().getTotalCount(), count, "重置搜索条件后记录数不对");			
	}
}
