package com.hteis.webtest.testcases;

import org.testng.*;
import org.testng.annotations.Test;

import com.hteis.webtest.business.*;
import com.hteis.webtest.common.DateUtil;
import com.hteis.webtest.entities.*;
import com.hteis.webtest.pages.ShopHomePage;
import com.hteis.webtest.pages.shop.CustDeclareConfirmDialog;
import com.hteis.webtest.pages.shop.CustDeclarePage;
import com.hteis.webtest.selenium.*;

/***客户申报测试*/
public class CustDeclareTest extends TCBase {
	
	@Test(suiteName="Sep", groups="购电业务", priority=1, testName="月度购电申报-成功",
			description = "验证交易场次建立后，有合同的用户可以进行申报")			
	public void Sep_MonthlyDeclare() throws Exception{
		CompanyData companyData = CompanyData.createCompanyData();		
		ContractData contractData = ContractData.createData(companyData.MainInfo.CompanyName, DateUtil.getNextMonthYear(), "大客户套餐");
		TradeFairData tradeFairData = TradeFairData.createMonthBidData(Constants.tradeCenterName1);		

		//创建一个购电用户并完成审批流程
		Company.createAndApproveBuyerCompany(companyData);
		
		//为用户创建一个下月所在年份的合同
		Driver.reLaunchBrowser();
		this.loginSep(Constants.saleTestUsr, Constants.saleTestPwd);		
		Contract.createAndApprove(contractData);
		Contract.Confirm(companyData.Admin.loginName, companyData.Admin.password);		
		
		//创建一个月度竞价交易场次并完成审批流程
		Driver.reLaunchBrowser();
		this.loginTcm(Constants.tradeTestUsr, Constants.tradeTestPwd);		
		TradeFair.createAndApprove(tradeFairData);
		
		//使用购电用户登录
		Driver.reLaunchBrowser();
		ShopHomePage homePage = this.LoginShop(companyData.Admin.loginName, companyData.Admin.password);
		
		//打开月度购电申报页面
		CustDeclarePage declarePage = homePage.openCustDeclarePage();
		
		//验证交易场次名称显示为刚创建的交易场次，交易周期为月度
		Assert.assertEquals(declarePage.getTradeFairName(), tradeFairData.Name, "月度申报页面交易场次名称不对");
		Assert.assertEquals(declarePage.getTradeFairPeriod(), tradeFairData.TradePeriod, "月度申报页面交易场次周期不对");
		
		//验证申报开始日期、申报截至日期正确
		Assert.assertEquals(DateUtil.getCNDateStr(declarePage.getDeclareStartDate()), DateUtil.getCNDateStr(tradeFairData.DeclareStartDate), "月度申报页面申报开始日期不对");
		Assert.assertEquals(DateUtil.getCNDateStr(declarePage.getDeclareEndDate()), DateUtil.getCNDateStr(tradeFairData.DeclareEndDate), "月度申报页面申报截止日期不对");
		
		//验证电压等级和申报状态正确
		Assert.assertEquals(declarePage.getVoltageLevel(), companyData.MainInfo.VoltageLevel, "月度申报页面电压等级错误");
		Assert.assertEquals(declarePage.getDeclareStatus(), "未申报");
		
		//填写申报电量(增量)
		int startMonth = Integer.parseInt(tradeFairData.TradeMonth.substring(5));
		float monthAmounts[] = contractData.getMonthAmounts(startMonth, tradeFairData.MonthCount);
		float diffAmounts[] = MonthDeclareData.createDiffAmounts(monthAmounts);
		MonthDeclareData declareData[] = MonthDeclareData.create(monthAmounts, diffAmounts, startMonth);		
		declarePage.setDiffAmount(diffAmounts);
		
		//验证申报数据表格中的月份只有一个月
		Assert.assertEquals(declarePage.getDeclareAmountTable().getBodyCount(), tradeFairData.MonthCount, "显示的月份表格数量不对");
		
		//验证申报数据表格中的月份和其他所有数据正确，环比和同比没有数据时显示为"-"
		declarePage.verifyDeclareData(declareData);
		
		//点击保存按钮
		declarePage.getSaveBtn().click();
		
		//验证提示消息：保存成功
		declarePage.verifyAlert("保存成功");
		declarePage.closeAlert();
		
		//刷新页面，验证页面数据正确
		Driver.refresh();
		declarePage.verifyDeclareData(declareData);	
		
		//点击提交
		declarePage.getSubmitBtn().click();
		CustDeclareConfirmDialog dialog = new CustDeclareConfirmDialog();
		
		//在确认对话框中验证交易场次名称和电压等级正确
		Assert.assertEquals(dialog.getTradeFairName(), tradeFairData.Name, "月度申报确认对话框交易场次名称不对");
		Assert.assertEquals(dialog.getVoltageLevel(), companyData.MainInfo.VoltageLevel, "月度申报确认对话框电压等级不对");
		
		//在确认对话框中验证月份和其他数据正确
		dialog.verifyDeclareData(declareData);
		
		//点击确认提交
		dialog.getConfirmBtn().click();
		
		//验证提示消息：提交成功
		//declarePage.verifyAlert("提交成功");
		
		//验证申报状态变为"已申报"
		Driver.wait(2000);
		Assert.assertEquals(declarePage.getDeclareStatus(), "已申报");
		
		//验证表格中的数据正确
		MonthDeclareData.updateLinkRatio(declareData);
		declarePage.verifyDeclareDataAfterSubmit(declareData);
	}
	
	@Test(suiteName="Sep", groups="购电业务", priority=1, testName="多月购电申报-成功",
			description="验证多月竞价交易场次建立后，有合同的用户可以进行申报\r\n"
					+ "验证多月交易场次申报时，按月度分开填写申报量")	
	public void Sep_MultiMonthlyDeclare() throws Exception{		
		CompanyData companyData = CompanyData.createCompanyData();		
		ContractData contractData = ContractData.createData(companyData.MainInfo.CompanyName, DateUtil.getNextMonthYear(), "大客户套餐");
		TradeFairData tradeFairData = TradeFairData.createMultiMonthBidData(Constants.tradeCenterName1);		

		//创建一个购电用户并完成审批流程
		Company.createAndApproveBuyerCompany(companyData);
		
		//为用户创建一个下月所在年份的合同
		Driver.reLaunchBrowser();
		this.loginSep(Constants.saleTestUsr, Constants.saleTestPwd);		
		Contract.createAndApprove(contractData);
		Contract.Confirm(companyData.Admin.loginName, companyData.Admin.password);		
		
		//创建一个多月竞价交易场次并完成审批流程
		Driver.reLaunchBrowser();
		this.loginTcm(Constants.tradeTestUsr, Constants.tradeTestPwd);		
		TradeFair.createAndApprove(tradeFairData);
		
		//使用购电用户登录
		Driver.reLaunchBrowser();
		ShopHomePage homePage = this.LoginShop(companyData.Admin.loginName, companyData.Admin.password);
		
		//打开月度购电申报页面
		CustDeclarePage declarePage = homePage.openCustDeclarePage();
		
		//验证交易场次名称显示为刚创建的交易场次，交易周期为多月
		Assert.assertEquals(declarePage.getTradeFairName(), tradeFairData.Name, "月度申报页面交易场次名称不对");
		Assert.assertEquals(declarePage.getTradeFairPeriod(), tradeFairData.TradePeriod, "月度申报页面交易场次周期不对");
		
		//验证申报开始日期、申报截至日期正确
		Assert.assertEquals(DateUtil.getCNDateStr(declarePage.getDeclareStartDate()), DateUtil.getCNDateStr(tradeFairData.DeclareStartDate), "月度申报页面申报开始日期不对");
		Assert.assertEquals(DateUtil.getCNDateStr(declarePage.getDeclareEndDate()), DateUtil.getCNDateStr(tradeFairData.DeclareEndDate), "月度申报页面申报截止日期不对");
		
		//验证电压等级和申报状态正确
		Assert.assertEquals(declarePage.getVoltageLevel(), companyData.MainInfo.VoltageLevel, "月度申报页面电压等级错误");
		Assert.assertEquals(declarePage.getDeclareStatus(), "未申报");
		
		//填写所有月份申报电量(增量)
		int startMonth = Integer.parseInt(tradeFairData.TradeMonth.substring(5));
		float monthAmounts[] = contractData.getMonthAmounts(startMonth, tradeFairData.MonthCount);
		float diffAmounts[] = MonthDeclareData.createDiffAmounts(monthAmounts);
		MonthDeclareData declareData[] = MonthDeclareData.create(monthAmounts, diffAmounts, startMonth);		
		declarePage.setDiffAmount(diffAmounts);
		
		//验证申报数据表格中的分月表格数量正确
		Assert.assertEquals(declarePage.getDeclareAmountTable().getBodyCount(), tradeFairData.MonthCount, "显示的分月表格数量不对");
		
		//验证每个分月表格中的月份和其他所有数据正确，环比和同比没有数据时显示为"-"
		declarePage.verifyDeclareData(declareData);
		
		//点击保存按钮
		declarePage.getSaveBtn().click();
		
		//验证提示消息：保存成功
		declarePage.verifyAlert("保存成功");
		declarePage.closeAlert();
		
		//刷新页面，验证页面数据正确
		Driver.refresh();
		declarePage.verifyDeclareData(declareData);	
		
		//点击提交
		declarePage.getSubmitBtn().click();
		CustDeclareConfirmDialog dialog = new CustDeclareConfirmDialog();
		
		//在确认对话框中验证交易场次名称和电压等级正确
		Assert.assertEquals(dialog.getTradeFairName(), tradeFairData.Name, "月度申报确认对话框交易场次名称不对");
		Assert.assertEquals(dialog.getVoltageLevel(), companyData.MainInfo.VoltageLevel, "月度申报确认对话框电压等级不对");
		
		//在确认对话框中验证月份和其他数据正确
		dialog.verifyDeclareData(declareData);
		
		//点击确认提交
		dialog.getConfirmBtn().click();
		
		//验证提示消息：提交成功
		//declarePage.verifyAlert("提交成功");
		
		//验证申报状态变为"已申报"
		Driver.wait(2000);
		Assert.assertEquals(declarePage.getDeclareStatus(), "已申报");
		
		//验证分月表格中的数据正确
		MonthDeclareData.updateLinkRatio(declareData);
		declarePage.verifyDeclareDataAfterSubmit(declareData);
	}
	
	@Test(suiteName="Sep", groups="购电业务", priority=2, testName="前六个月申报情况图表",
			description="验证月度购电申报页面里的前六个月申报情况图表数据正确")	
	public void Sep_CustDeclareLast6MonthChart() throws Exception{	
		
	}
	
	@Test(suiteName="Sep", groups="购电业务", priority=2, testName="购电申报同比和环比增幅",
			description="验证月度购电申报页面里的同比和环比增幅数据正确")	
	public void Sep_CustDeclareCompareData(){
		
	}
}
