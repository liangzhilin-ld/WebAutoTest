package com.hteis.webtest.testcases;

import java.util.Date;

import org.testng.*;
import org.testng.annotations.Test;

import com.hteis.webtest.business.CustComplaint;
import com.hteis.webtest.common.DateUtil;
import com.hteis.webtest.entities.*;
import com.hteis.webtest.pages.cust.*;
import com.hteis.webtest.selenium.Driver;
import com.hteis.webtest.selenium.HtmlNgPager;
import com.hteis.webtest.selenium.HtmlRow;
import com.hteis.webtest.selenium.ICheckRow;

public class CustComplaintTest extends TCBase {
	
	@Test(suiteName = "Sep", groups = "客户管理", priority = 1, testName = "创建客户投诉-成功", 
			description = "验证数据有效时客户投诉可创建成功\r\n"
					+ "当电话为注册用户电话时，投诉人和客户名称可自动获取且不可更改\r\n" 
					+ "当客户为非注册用户时，投诉人和客户名称可任意输入\r\n" 
					+ "客户投诉详情页面的客户服务数据显示正确")
	public void CustComplaintNew01() throws Exception { 
		//使用客户专员账号登录系统
		CustHomePage homePage = this.loginCust(Constants.custTestUsr, Constants.custTestPwd);

		//打开客户服务管理页面
		CustComplaintPage custPage = homePage.openCustComplaintPage();

		//点击创建投诉按钮
		custPage.getNewBtn().click();
		
		//验证对话框中业务状态默认为处理中
		CustComplaintNewDialog newDialog = new CustComplaintNewDialog();
		Assert.assertEquals(newDialog.getStatusSelect().getSelectedText(), "处理中");		
		
		//输入一个已注册的用户的手机号，然后输入其他字段
		ComplaintData regCustData = ComplaintData.createBigCustData();
		newDialog.setFieldValues(regCustData);
		
		//验证投诉人和客户名称已自动填充且不可更改
		Assert.assertEquals(newDialog.getComplainantInput().getText(), regCustData.Complainant, "投诉人未自动设置");
		Assert.assertEquals(newDialog.getCustNameInput().getText(), regCustData.CustName, "客户名称未自动设置");
		Assert.assertTrue(newDialog.getComplainantInput().isReadOnly(), "投诉人未设为只读");
		Assert.assertTrue(newDialog.getCustNameInput().isReadOnly(), "客户名称未设为只读");
		
		//点击保存按钮
		newDialog.getSaveBtn().click();
		custPage.verifyAlert("保存成功");
		custPage.closeAlert();
		
		//验证新创建的投诉显示在表中，验证数据正确
		custPage.verifyData(regCustData);
		
		//点击详情，验证详情页面数据正确
		CustComplaintDetailsDialog detailDialog = custPage.openDetailsDialog(regCustData);
		detailDialog.verifyData(regCustData);		
		detailDialog.getCloseBtn().click();
		Driver.wait(1000);
		
		//点击创建投诉按钮
		custPage.getNewBtn().click();
		
		//输入一个未注册的用户的手机号，然后输入其他字段
		ComplaintData smallCustData = ComplaintData.createSmallCustData();
		newDialog.setFieldValues(smallCustData);
		
		//点击保存按钮
		newDialog.getSaveBtn().click();
		custPage.verifyAlert("保存成功");
		custPage.closeAlert();
		
		//验证新创建的投诉显示在表中，验证数据正确
		custPage.verifyData(regCustData);
		
		//点击详情，验证详情页面数据正确
		custPage.openDetailsDialog(regCustData);
		detailDialog.verifyData(regCustData);		
		detailDialog.getCloseBtn().click();
		Driver.wait(1000);
	}
	
	@Test(suiteName = "Sep", groups = "客户管理", priority = 3, testName = "创建客户投诉-失败", 
			description = "测试字段验证及错误提示消息")
	public void CustComplaintNew02() throws Exception { 
		
	}
	
	@Test(suiteName = "Sep", groups = "客户管理", priority = 2, testName = "编辑客户投诉", 
			description = "验证客户投诉可以编辑\r\n"
					+ "编辑客户投诉时只有投诉描述和处理人可编辑")
	public void CustComplaintEdit() throws Exception { 
		//使用客户专员账号登录系统
		this.loginCust(Constants.custTestUsr, Constants.custTestPwd);
		
		//创建一个大客户投诉
		ComplaintData bigCustData = ComplaintData.createBigCustData();
		CustComplaint.create(bigCustData);
		
		//点击编辑按钮编辑投诉
		CustComplaintPage custPage = new CustComplaintPage();
		CustComplaintEditDialog editDialog = custPage.openEditDialog(bigCustData);
		
		//验证电话/投诉人/客户名称/投诉类型/业务状态不可编辑
		Assert.assertTrue(editDialog.getPhoneInput().isReadOnly(), "电话字段非只读");
		Assert.assertTrue(editDialog.getComplainantInput().isReadOnly(), "投诉人字段非只读");
		Assert.assertTrue(editDialog.getCustNameInput().isReadOnly(), "客户名称非只读");
		Assert.assertTrue(editDialog.getComplaintTypeInput().isReadOnly(), "投诉类型非只读");
		Assert.assertTrue(editDialog.getStatusInput().isReadOnly(), "业务状态非只读");
		
		//修改客户投诉和责任人字段
		bigCustData.update();
		editDialog.setFieldValue(bigCustData);
		
		//点击保存，验证提示消息：保存成功
		editDialog.getSaveBtn().click();
		custPage.verifyAlert("保存成功");
		custPage.closeAlert();
		
		//验证表中的数据已修改
		custPage.verifyData(bigCustData);
		
		//创建一个非大客户投诉
		ComplaintData smallCustData = ComplaintData.createBigCustData();
		CustComplaint.create(smallCustData);
		
		//点击编辑按钮编辑投诉
		custPage.openEditDialog(smallCustData);
		
		//验证电话/投诉人/客户名称/投诉类型/业务状态不可编辑
		Assert.assertTrue(editDialog.getPhoneInput().isReadOnly(), "电话字段非只读");
		Assert.assertTrue(editDialog.getComplainantInput().isReadOnly(), "投诉人字段非只读");
		Assert.assertTrue(editDialog.getCustNameInput().isReadOnly(), "客户名称非只读");
		Assert.assertTrue(editDialog.getComplaintTypeInput().isReadOnly(), "投诉类型非只读");
		Assert.assertTrue(editDialog.getStatusInput().isReadOnly(), "业务状态非只读");
		
		//修改客户投诉和责任人字段
		smallCustData.update();
		editDialog.setFieldValue(smallCustData);
		
		//点击保存，验证提示消息：保存成功
		editDialog.getSaveBtn().click();
		custPage.verifyAlert("保存成功");
		custPage.closeAlert();
		
		//验证表中的数据已修改
		custPage.verifyData(smallCustData);
	}
	
	@Test(suiteName = "Sep", groups = "客户管理", priority = 1, testName = "处理客户投诉", 
			description = "验证客户投诉处理功能\r\n"
						+ "验证不同角色的可用操作正确\r\n"
						+ "验证处理前后的可用操作正确\r\n"
						+ "验证处理后详情对话框内会显示处理的信息")
	public void CustComplaintHandle() throws Exception { 
		//使用客户专员账号登录系统
		this.loginCust(Constants.custTestUsr, Constants.custTestPwd);
		
		//创建一个大客户投诉
		ComplaintData bigCustData = ComplaintData.createBigCustData();
		CustComplaint.create(bigCustData);
		
		//验证客户投诉的可用操作为”编辑“和“详情"
		String[] ops = new String[] {"编辑", "详情"};
		CustComplaintPage custPage = new CustComplaintPage();
		custPage.verifyOperations(bigCustData, ops);		
		
		//使用客户经理登录
		Driver.reLaunchBrowser();
		CustHomePage homePage = this.loginCust(Constants.custManagerUsr, Constants.custManagerPwd);
		
		//打开客户投诉处理页面
		custPage = homePage.openCustComplaintHandlePage();
		
		//验证客户投诉的可用操作为“处理"和“详情”
		ops = new String[] {"处理", "详情"};
		custPage.verifyOperations(bigCustData, ops);	
		
		//点击处理
		CustComplaintHandleDialog handleDialog = custPage.openHandleDialog(bigCustData);
		
		//验证业务状态已自动设为已处理
		Assert.assertEquals(handleDialog.getStatusSelect().getSelectedText(), "已处理");
		
		//输入处理人、处理详情，选择客户反馈
		bigCustData.addHandleData();
		handleDialog.setFieldValues(bigCustData);
		
		//点击保存按钮，验证提示消息：保存成功
		handleDialog.getSaveBtn().click();
		custPage.verifyAlert("保存成功");
		custPage.closeAlert();
		
		//验证表中数据已更新
		custPage.verifyData(bigCustData);
		
		//验证可用的操作变为“处理"、“关闭"、"详情"
		custPage.verifyOperations(bigCustData, new String[]{"处理", "关闭", "详情"});
		
		//点击详情
		CustComplaintDetailsDialog detailsDialog = custPage.openDetailsDialog(bigCustData);
		detailsDialog.verifyData(bigCustData);		
		
		//使用客户专员账号登录系统
		Driver.reLaunchBrowser();
		homePage = this.loginCust(Constants.custTestUsr, Constants.custTestPwd);
		
		//打开客户投诉录入页面
		custPage = homePage.openCustComplaintPage();
		
		//找到刚才处理的客户投诉，验证可用的操作只有详情
		custPage.verifyOperations(bigCustData, new String[] {"详情"});
	}
	
	@Test(suiteName = "Sep", groups = "客户管理", priority = 1, testName = "关闭客户投诉", 
			description = "验证客户投诉关闭功能\r\n"
						+ "验证不同角色的可用操作正确\r\n"
						+ "验证关闭前后的可用操作正确\r\n"
						+ "验证关闭后详情对话框内会显示投诉关闭人")
	public void CustComplaintClose() throws Exception { 
		//使用客户专员账号登录系统
		this.loginCust(Constants.custTestUsr, Constants.custTestPwd);
		
		//创建一个大客户投诉并处理
		ComplaintData bigCustData = ComplaintData.createBigCustData();
		CustComplaint.createAndHandle(bigCustData);		
		
		//点击关闭打开关闭对话框
		CustComplaintPage custPage = new CustComplaintPage();
		CustComplaintCloseDialog closeDialog = custPage.openCloseDialog(bigCustData);
		bigCustData.addCloseData();
		
		//选中确认投诉关闭，输入投诉关闭人
		closeDialog.setFieldValues(bigCustData);
		
		//点击保存，验证提示消息：保存成功
		closeDialog.getSaveBtn().click();
		custPage.verifyAlert("保存成功");
		custPage.closeAlert();
		Driver.wait(1000);
		
		//验证此时可用的操作是“详情“
		custPage.verifyOperations(bigCustData, new String[]{"详情"});
		
		//点击详情
		CustComplaintDetailsDialog detailDialog = custPage.openDetailsDialog(bigCustData);
		
		//验证详情对话框中数据正确
		detailDialog.verifyData(bigCustData);
		
		//使用客户专员账号登录系统
		Driver.reLaunchBrowser();
		CustHomePage homePage = this.loginCust(Constants.custTestUsr, Constants.custTestPwd);
		
		//打开客户投诉录入页面
		custPage = homePage.openCustComplaintPage();
		
		//找到刚才处理的客户投诉，验证可用的操作只有详情
		custPage.verifyOperations(bigCustData, new String[] {"详情"});
	}
	
	@Test(suiteName = "Sep", groups = "客户管理", priority = 2, testName = "查询客户服务记录", 
			description = "验证客户服务记录可按--客户名称/投诉时间/投诉类型/处理时间/投诉人/业务状态/是否大客户--查询\r\n"
					+ "验证重置功能可清除所有查询条件并显示所有投诉\r\n" 
					+ "验证没有设置任何搜索条件时查询将显示所有投诉")
	public void CustComplaintSearch() throws Exception {
		
		int loadTime = 2000;

		// 使用客户专员账号登录系统
		CustHomePage homePage = this.loginCust(Constants.custTestUsr, Constants.custTestPwd);

		// 打开客户投诉录入页面
		CustComplaintPage custPage = homePage.openCustComplaintPage();
		Driver.wait(loadTime);
		HtmlNgPager pager = custPage.getPager();		
		int count = pager.getTotalCount();

		// 使用客户名称搜索：单位名称包含"绿叶", 验证搜索结果正确
		String cmpy = "绿叶";
		custPage.getCustNameInput().setText(cmpy);
		custPage.getQueryBtn().click();
		Driver.wait(loadTime);
		
		HtmlRow result = custPage.getComplaintTable().VerifyAllRows(custPage.getPager(), new ICheckRow() {
			public boolean CheckRow(HtmlRow row) {
				return row.getCellValue(CustComplaintPage.CustNameColNo).contains(cmpy);
			}
		});
		Assert.assertNull(result, "按客户名称的搜索结果不对，不符合条件的行：" + custPage.printData(result));
		custPage.getCustNameInput().clear();	
		
		
		final Date startTime1 = DateUtil.getDateTimeFromString("2017-03-15 00:00:00");
		final Date startTime2 = DateUtil.getDateTimeFromString("2017-03-16 00:00:00");
		final Date endTime = DateUtil.getDateTimeFromString("2017-03-17 23:59:59");
		
		// 使用投诉时间搜索： >= 2017-03-15, 验证搜索结果正确			
		custPage.getCreateStartTimePicker().setText(DateUtil.getCNDateStr(startTime1));
		custPage.getQueryBtn().click();
		Driver.wait(loadTime);
		
		result = custPage.getComplaintTable().VerifyAllRows(custPage.getPager(), new ICheckRow() {
			public boolean CheckRow(HtmlRow row) throws Exception {
				Date createDate = DateUtil.getDateTimeFromString(row.getCellValue(CustComplaintPage.CreateTimeColNo));
				return createDate.getTime() >= startTime1.getTime();
			}
		});
		Assert.assertNull(result, "按记录时间段开始时间搜索结果不对，不符合条件的行：" + custPage.printData(result));
		custPage.getCreateStartTimePicker().clear();

		// 使用投诉时间搜索：<=2017-03-17, 验证搜索结果正确	
		custPage.getCreateEndTimePicker().setText(DateUtil.getCNDateStr(endTime));
		custPage.getQueryBtn().click();
		Driver.wait(loadTime);
		
		result = custPage.getComplaintTable().VerifyAllRows(custPage.getPager(), new ICheckRow() {
			public boolean CheckRow(HtmlRow row) throws Exception {
				Date createDate = DateUtil.getDateTimeFromString(row.getCellValue(CustComplaintPage.CreateTimeColNo));			
				return createDate.getTime() <= endTime.getTime();
			}
		});
		Assert.assertNull(result, "按记录时间段结束时间搜索结果不对，不符合条件的行：" + custPage.printData(result));
		
		// 按记录时间搜索:  2017-03-17>=记录时间 >= 2017-03-15
		custPage.getCreateStartTimePicker().setText(DateUtil.getCNDateStr(startTime1));
		custPage.getQueryBtn().click();
		Driver.wait(loadTime);
		
		result = custPage.getComplaintTable().VerifyAllRows(custPage.getPager(), new ICheckRow() {
			public boolean CheckRow(HtmlRow row) throws Exception {
				Date createDate = DateUtil.getDateTimeFromString(row.getCellValue(CustComplaintPage.CreateTimeColNo));
				return createDate.getTime() <= endTime.getTime() && createDate.getTime() >= startTime1.getTime();
			}
		});
		Assert.assertNull(result, "按记录时间段开始时间和结束时间搜索结果不对，不符合条件的行：" + custPage.printData(result));
		custPage.getCreateStartTimePicker().clear();
		custPage.getCreateEndTimePicker().clear();
		
		// 按用户类型搜索: 投诉类型=产品，验证搜索结果正确
		String type = "产品";		
		custPage.getComplaintTypeSelect().selectByText(type);
		custPage.getQueryBtn().click();
		Driver.wait(loadTime);
		
		result = custPage.getComplaintTable().VerifyAllRows(custPage.getPager(), new ICheckRow() {
			public boolean CheckRow(HtmlRow row) throws Exception {
				return row.getCellValue(CustComplaintPage.ComplaintTypeColNo).equals(type);
			}
		});
		Assert.assertNull(result, "按投诉类型搜索结果不对，不符合条件的行：" + custPage.printData(result));
		custPage.getComplaintTypeSelect().selectByIndex(0);
		
		
		// 使用处理时间搜索： >= 2017-03-15, 验证搜索结果比>=2017-03-16多1
		custPage.getHandleStartTimePicker().setText(DateUtil.getCNDateStr(startTime1));
		custPage.getQueryBtn().click();
		Driver.wait(loadTime);
		int count1 = custPage.getPager().getTotalCount();
		
		custPage.getHandleStartTimePicker().setText(DateUtil.getCNDateStr(startTime2));
		custPage.getQueryBtn().click();
		Driver.wait(loadTime);
		int count2 = custPage.getPager().getTotalCount();
		
		Assert.assertEquals(count1-count2, 1, "按处理时间段开始时间搜索结果不对");

		// 使用处理时间搜索：<=2017-03-17, 验证搜索结果正确
		custPage.getHandleStartTimePicker().clear();
		custPage.getHandleEndTimePicker().setText(DateUtil.getCNDateStr(endTime));
		custPage.getQueryBtn().click();
		Driver.wait(loadTime);	
		
		Assert.assertEquals(custPage.getPager().getTotalCount(), 11, "按处理时间段结束时间搜索结果不对");
		
		// 按处理时间搜索:  2017-03-16>=记录时间 >= 2017-03-17
		custPage.getHandleStartTimePicker().setText(DateUtil.getCNDateStr(startTime2));
		custPage.getQueryBtn().click();
		Driver.wait(loadTime);
		
		Assert.assertEquals(custPage.getPager().getTotalCount(), 10, "按处理时间段搜索结果不对");
		custPage.getHandleEndTimePicker().clear();
		custPage.getHandleStartTimePicker().clear();
		
		// 按投诉人搜索：投诉人=王霸
		String complainant = "王霸";
		custPage.getComplainantInput().input(complainant);
		custPage.getQueryBtn().click();
		Driver.wait(loadTime);

		Assert.assertEquals(pager.getTotalCount(), 1, "按投诉人搜索结果不对");
		custPage.getComplainantInput().clear();
		
		// 按状态搜索：业务状态=关闭
		String status = "关闭";
		custPage.getStatusSelect().selectByText(status);
		custPage.getQueryBtn().click();
		Driver.wait(loadTime);
		
		result = custPage.getComplaintTable().VerifyAllRows(custPage.getPager(), new ICheckRow() {
			public boolean CheckRow(HtmlRow row) throws Exception {
				return row.getCellValue(CustComplaintPage.StatusColNo).equals(status);
			}
		});
		Assert.assertNull(result, "按业务状态搜索结果不对，不符合条件的行：" + custPage.printData(result));
		custPage.getStatusSelect().selectByIndex(0);
		
		// 按是否大客户搜索：是
		Boolean isBigCust = true;
		if(isBigCust){
			custPage.getIsBigCustYesRb().check();
		} else {
			custPage.getIsBigCustNoRb().check();
		}
		custPage.getQueryBtn().click();
		Driver.wait(loadTime);
		
		result = custPage.getComplaintTable().VerifyAllRows(custPage.getPager(), new ICheckRow() {
			public boolean CheckRow(HtmlRow row) throws Exception {
				return row.getCellValue(CustComplaintPage.IsBigCustColNo).equals(isBigCust ? "是" : "否");
			}
		});
		Assert.assertNull(result, "按是否大客户搜索结果不对，不符合条件的行：" + custPage.printData(result));
		custPage.getCustNameInput().clear();
		
		// 使用复合条件查询：客户名称(四川)， 是否大客户：是，投诉类型：产品，投诉人:凤，状态处理中，记录时间2017-03-17~2017-03-17
		custPage.getCustNameInput().setText("四川");
		custPage.getComplaintTypeSelect().selectByText("产品");
		custPage.getIsBigCustYesRb().check();
		custPage.getComplainantInput().setText("凤");
		custPage.getStatusSelect().selectByText("处理中");
		custPage.getCreateStartTimePicker().setText(DateUtil.getCNDateStr(startTime1));
		custPage.getCreateEndTimePicker().setText(DateUtil.getCNDateStr(endTime));
		custPage.getQueryBtn().click();
		Driver.wait(loadTime);		
		
		Assert.assertEquals(custPage.getPager().getTotalCount(), 4, "按复合搜索结果不对");
		
		// 点击重置按钮
		custPage.getResetBtn().click();
		Driver.wait(loadTime);
		
		// 验证搜索条件北重置
		Assert.assertEquals(custPage.getCustNameInput().getText(), "", "客户名称未重置");
		Assert.assertEquals(custPage.getIsBigCustNoRb().isChecked(), false, "是否大客户未重置");
		Assert.assertEquals(custPage.getIsBigCustYesRb().isChecked(), false, "是否大客户未重置");
		Assert.assertEquals(custPage.getComplaintTypeSelect().getSelectedIndex(), 0, "投诉类型未重置");
		Assert.assertEquals(custPage.getCreateStartTimePicker().getText(), "", "记录时间段开始时间未重置");
		Assert.assertEquals(custPage.getCreateEndTimePicker().getText(), "", "记录时间段结束时间未重置");
		Assert.assertEquals(custPage.getHandleStartTimePicker().getText(), "", "处理时间段开始时间未重置");
		Assert.assertEquals(custPage.getHandleStartTimePicker().getText(), "", "处理时间段结束时间未重置");
		Assert.assertEquals(custPage.getComplainantInput().getText(), "", "投诉人未重置");
		Assert.assertEquals(custPage.getStatusSelect().getSelectedIndex(), 0, "业务状态未重置");
		
		// 验证表中显示了所有记录
		Assert.assertEquals(custPage.getPager().getTotalCount(), count, "重置搜索条件后记录数不对");	
		
		//点击查询，验证表中显示了所有记录
		custPage.getQueryBtn().click();
		Driver.wait(loadTime);	
		Assert.assertEquals(custPage.getPager().getTotalCount(), count, "重置搜索条件后记录数不对");	
	}
}
