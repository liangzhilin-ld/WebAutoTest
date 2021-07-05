package com.hteis.webtest.testcases;

import java.util.Date;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.hteis.webtest.entities.Constants;
import com.hteis.webtest.entities.TradeFairData;
import com.hteis.webtest.pages.*;
import com.hteis.webtest.pages.trade.*;
import com.hteis.webtest.selenium.*;
import com.hteis.webtest.business.*;
import com.hteis.webtest.common.DateUtil;

public class TradeFairTest extends TCBase {


	@Test(suiteName="Sep", groups="交易管理", priority=1, testName="创建月度竞价交易-成功",
			description="填入正确数据后可创建月度竞价交易场次\r\n"
					+ "月度竞价交易场次创建后状态为编辑")
	public void Sep_TradeFairNew01() throws Exception{
		// 使用交易员登录https://ttcm.hteis.cn/
		TcmHomePage homePage = this.loginTcm(Constants.tradeTestUsr, Constants.tradeTestPwd);
		
		//打开交易管理->交易场次管理
		TradeFairPage tradeFairPage = homePage.openTradeFairPage();
		
		//点击新增按钮
		tradeFairPage.getAddLink().click();
		
		//输入月度竞价交易数据
		TradeFairData tradeFairData = TradeFairData.createMonthBidData(Constants.tradeCenterName1);
		TradeFairNewPage newPage = new TradeFairNewPage();
		newPage.setFieldValues(tradeFairData);
		
		//点击确定按钮
		newPage.getConfirmBtn().click();
		
		//验证提示消息：保存成功
		newPage.verifyAlert("保存成功");
		
		//验证新建的交易场次出现在表格中
		tradeFairPage.verifyTradeFairData(tradeFairData);
		
		//点击交易场次名称
		tradeFairPage.openTradeFair(tradeFairData.Name);
		
		//在交易场次编辑对话框中验证数据正确
		TradeFairEditPage editPage = new TradeFairEditPage();		
		editPage.verifyData(tradeFairData);
		
		//清楚数据
		editPage.getDelBtn().click();
		editPage.ClickConfirm();
	}
	
	@Test(suiteName="Sep", groups="交易管理", priority=2, testName="编辑月度竞价交易场次",
			description="验证月度竞价交易场次可编辑")
	public void Sep_TradeFairEdit01() throws Exception{
		//使用交易员登录https://ttcm.hteis.cn/
		TcmHomePage homePage = this.loginTcm(Constants.tradeTestUsr, Constants.tradeTestPwd);
		
		//创建一个月度竞价交易场次
		TradeFairData tradeFairData = TradeFairData.createMonthBidData(Constants.tradeCenterName1);
		TradeFair.create(tradeFairData);
		
		//点击交易场次名称打开编辑页面
		TradeFairPage tradeFairPage = homePage.openTradeFairPage();
		tradeFairPage.openTradeFair(tradeFairData.Name);
		
		//更改除交易周期和品种外的所有能更改的字段
		TradeFairData.updateMonthBidData(tradeFairData);
		TradeFairEditPage editPage = new TradeFairEditPage();
		editPage.setFieldValues(tradeFairData);
		
		//点击保存按钮
		editPage.getSaveBtn().click();
		editPage.verifyAlert("保存成功");
		
		//验证交易场次表中的数据已更新
		tradeFairPage.verifyTradeFairData(tradeFairData);		
		
		//清除数据
		tradeFairPage.deleteTradeFair(tradeFairData.Name);
	}
	
	@Test(suiteName="Sep", groups="交易管理", priority=1, testName="创建年度协商交易-成功",
			description="填入正确数据后可创建年度交易场次\r\n"
					+ "年度交易场次创建后状态为编辑")
	public void Sep_TradeFairNew02() throws Exception{
		//使用交易员登录https://ttcm.hteis.cn/
		TcmHomePage homePage = this.loginTcm(Constants.tradeTestUsr, Constants.tradeTestPwd);
		
		//打开交易管理->交易场次管理
		TradeFairPage tradeFairPage = homePage.openTradeFairPage();
		
		//点击新增按钮
		tradeFairPage.getAddLink().click();
		
		//输入年度协商交易数据
		TradeFairData tradeFairData = TradeFairData.createYearNegotiateData(Constants.tradeCenterName1);
		TradeFairNewPage newPage = new TradeFairNewPage();
		newPage.setFieldValues(tradeFairData);
		
		//点击确定按钮
		newPage.getConfirmBtn().click();
		
		//验证提示消息：保存成功
		newPage.verifyAlert("保存成功");
		
		//验证新建的交易场次出现在表格中
		tradeFairPage.verifyTradeFairData(tradeFairData);
		
		//点击交易场次名称
		tradeFairPage.openTradeFair(tradeFairData.Name);
		
		//在交易场次编辑对话框中验证数据正确
		TradeFairEditPage editPage = new TradeFairEditPage();
		editPage.verifyData(tradeFairData);	
		
		//清除数据
		editPage.getDelBtn().click();
		editPage.ClickConfirm();
	}
	
	@Test(suiteName="Sep", groups="交易管理", priority=2, testName="编辑年度协商交易场次",
			description="验证年度协商交易场次可编辑")
	public void Sep_TradeFairEdit02() throws Exception{
		//使用交易员登录https://ttcm.hteis.cn/
		TcmHomePage homePage = this.loginTcm(Constants.tradeTestUsr, Constants.tradeTestPwd);
		
		//创建一个年度协商交易场次
		TradeFairData tradeFairData = TradeFairData.createYearNegotiateData(Constants.tradeCenterName1);
		TradeFair.create(tradeFairData);
		
		//点击交易场次名称打开编辑页面
		TradeFairPage tradeFairPage = homePage.openTradeFairPage();
		tradeFairPage.openTradeFair(tradeFairData.Name);
		
		//更改除交易周期和品种外的所有能更改的字段
		TradeFairData.updateYearNegotiateData(tradeFairData);
		TradeFairEditPage editPage = new TradeFairEditPage();
		editPage.setFieldValues(tradeFairData);
		
		//点击保存按钮
		editPage.getSaveBtn().click();
		editPage.verifyAlert("保存成功");
		
		//验证交易场次表中的数据已更新
		tradeFairPage.verifyTradeFairData(tradeFairData);		
		
		//清除数据
		tradeFairPage.deleteTradeFair(tradeFairData.Name);
	}
	
	@Test(suiteName="Sep", groups="交易管理", priority=1, testName="创建多月协商交易-成功",
			description="填入正确数据后可创建多月协商交易场次\r\n"
					+ "多月协商交易场次创建后状态为编辑")
	public void Sep_TradeFairNew03() throws Exception{
		//使用交易员登录https://ttcm.hteis.cn/
		TcmHomePage homePage = this.loginTcm(Constants.tradeTestUsr, Constants.tradeTestPwd);
		
		//打开交易管理->交易场次管理
		TradeFairPage tradeFairPage = homePage.openTradeFairPage();
		
		//点击新增按钮
		tradeFairPage.getAddLink().click();
		
		//输入年度协商交易数据
		TradeFairData tradeFairData = TradeFairData.createMultiMonthBidData(Constants.tradeCenterName1);
		TradeFairNewPage newPage = new TradeFairNewPage();
		newPage.setFieldValues(tradeFairData);
		
		//点击确定按钮
		newPage.getConfirmBtn().click();
		
		//验证提示消息：保存成功
		newPage.verifyAlert("保存成功");
		
		//验证新建的交易场次出现在表格中
		tradeFairPage.verifyTradeFairData(tradeFairData);
		
		//点击交易场次名称
		tradeFairPage.openTradeFair(tradeFairData.Name);
		
		//在交易场次编辑对话框中验证数据正确
		TradeFairEditPage editPage = new TradeFairEditPage();
		editPage.verifyData(tradeFairData);	
		
		//清除数据
		editPage.getDelBtn().click();
		editPage.ClickConfirm();
	}
	
	@Test(suiteName="Sep", groups="交易管理", priority=2, testName="编辑多月竞价交易场次",
			description="验证多月竞价交易场次可编辑")
	public void Sep_TradeFairEdit03() throws Exception{
		//使用交易员登录https://ttcm.hteis.cn/
		TcmHomePage homePage = this.loginTcm(Constants.tradeTestUsr, Constants.tradeTestPwd);
		
		//创建一个多月竞价交易场次
		TradeFairData tradeFairData = TradeFairData.createMultiMonthBidData(Constants.tradeCenterName1);
		TradeFair.create(tradeFairData);
		
		//点击交易场次名称打开编辑页面
		TradeFairPage tradeFairPage = homePage.openTradeFairPage();
		TradeFairEditPage editPage = tradeFairPage.openTradeFair(tradeFairData.Name);
		
		//更改除交易周期和品种外的所有能更改的字段
		TradeFairData.updateMultiMonthBidData(tradeFairData);
		editPage.setFieldValues(tradeFairData);
		
		//点击保存按钮
		editPage.getSaveBtn().click();
		editPage.verifyAlert("保存成功");
		
		//验证交易场次表中的数据已更新
		tradeFairPage.verifyTradeFairData(tradeFairData);		
		
		//清除数据
		tradeFairPage.deleteTradeFair(tradeFairData.Name);
	}
	
	@Test(suiteName="Sep", groups="交易管理", priority=2, testName="删除交易场次",
			description="验证编辑状态的交易场次可删除")
	public void Sep_TradeFairDelete() throws Exception{
		//使用交易员登录https://ttcm.hteis.cn/
		this.loginTcm(Constants.tradeTestUsr, Constants.tradeTestPwd);
		
		//创建一个月度竞价交易场次
		TradeFairData tradeFairData = TradeFairData.createMonthBidData(Constants.tradeCenterName1);
		TradeFair.create(tradeFairData);
		
		//点击场次名称打开编辑页面，点击删除按钮
		//点是确认删除
		//验证提示消息：删除成功
		TradeFairPage tradeFairPage = new TradeFairPage();
		tradeFairPage.deleteTradeFair(tradeFairData.Name);
		Driver.wait(1000);
		
		//验证：交易场次从表中删除
		Assert.assertNull(tradeFairPage.getTradeFairTable().findRow(TradeFairPage.NameColNo, tradeFairData.Name), "交易场次未删除");
	}
	
	@Test(suiteName="Sep", groups="交易管理", priority=2, testName="搜索交易场次",
			description="验证交易场次可按交易周期搜索\r\n"
					+ "验证交易场次可按交易日期搜索\r\n"
					+ "验证搜索条件可重置，重置后显示所有交易场次\r\n"
					+ "验证未设置搜索条件时点查询显示所有交易场次")
	public void Sep_TradeFairSearch() throws Exception{
		
		int loadTime = 2000;
		
		//使用交易员登录https://ttcm.hteis.cn/
		TcmHomePage homePage = this.loginTcm(Constants.tradeTestUsr, Constants.tradeTestPwd);
		
		//打开交易场次管理页面
		TradeFairPage tradeFairPage = homePage.openTradeFairPage();
		Driver.wait(loadTime);
		int count = tradeFairPage.getPager().getTotalCount();
		
		//输入查询条件 交易周期：年度，验证搜索结果正确
		final String period = "月度";
		tradeFairPage.getTradePeriodSelect().selectByText(period);
		tradeFairPage.getQueryBtn().click();
		Driver.wait(loadTime);
		HtmlRow result = tradeFairPage.getTradeFairTable().VerifyAllRows(tradeFairPage.getPager(), new ICheckRow() {
			public boolean CheckRow(HtmlRow row) throws Exception {
				return row.getCellValue(TradeFairPage.TradePeriodColNo).equals(period);
			}
		});
		Assert.assertNull(result, "按交易周期搜索结果不对，不符合条件的行：" + tradeFairPage.printData(result));
		tradeFairPage.getTradePeriodSelect().selectByIndex(0);
		
		//输入查询条件:交易日期>=2017-04-30，验证搜索结果正确
		Date startDate = DateUtil.getDateFromString("2017-03-31");
		Date endDate = DateUtil.getDateFromString("2017-04-30");
		tradeFairPage.getTradeStartDatePicker().setText(DateUtil.getCNDateStr(startDate));
		tradeFairPage.getQueryBtn().click();
		Driver.wait(loadTime);
		result = tradeFairPage.getTradeFairTable().VerifyAllRows(tradeFairPage.getPager(), new ICheckRow() {
			public boolean CheckRow(HtmlRow row) throws Exception {
				Date tradeDate = DateUtil.getDateFromString(row.getCellValue(TradeFairPage.TradeDateColNo));
				return tradeDate.getTime() >= startDate.getTime();
			}
		});
		Assert.assertNull(result, "按交易日期下限搜索结果不对，不符合条件的行：" + tradeFairPage.printData(result));
		tradeFairPage.getTradeStartDatePicker().clear();
		
		//输入查询条件:交易日期<=2017-12-30，验证搜索结果正确
		tradeFairPage.getTradeEndDatePicker().setText(DateUtil.getCNDateStr(endDate));
		tradeFairPage.getQueryBtn().click();
		Driver.wait(loadTime);
		result = tradeFairPage.getTradeFairTable().VerifyAllRows(tradeFairPage.getPager(), new ICheckRow() {
			public boolean CheckRow(HtmlRow row) throws Exception {
				Date tradeDate = DateUtil.getDateFromString(row.getCellValue(TradeFairPage.TradeDateColNo));
				return tradeDate.getTime() <= endDate.getTime();
			}
		});
		Assert.assertNull(result, "按交易日期上限搜索结果不对，不符合条件的行：" + tradeFairPage.printData(result));
		
		//输入查询条件：交易日期 2017-04-30 ~ 2017-12-30，验证搜索结果正确
		tradeFairPage.getTradeStartDatePicker().setText(DateUtil.getCNDateStr(startDate));
		tradeFairPage.getQueryBtn().click();
		Driver.wait(loadTime);
		result = tradeFairPage.getTradeFairTable().VerifyAllRows(tradeFairPage.getPager(), new ICheckRow() {
			public boolean CheckRow(HtmlRow row) throws Exception {
				Date tradeDate = DateUtil.getDateFromString(row.getCellValue(TradeFairPage.TradeDateColNo));
				return tradeDate.getTime() >= startDate.getTime() && tradeDate.getTime() <= endDate.getTime();
			}
		});
		Assert.assertNull(result, "按交易日期范围搜索结果不对，不符合条件的行：" + tradeFairPage.printData(result));
		
		//输入查询条件：交易周期=月度，交易日期：2017-03-31 ~ 2017-04-30
		tradeFairPage.getTradePeriodSelect().selectByText(period);
		tradeFairPage.getQueryBtn().click();
		Driver.wait(loadTime);
		result = tradeFairPage.getTradeFairTable().VerifyAllRows(tradeFairPage.getPager(), new ICheckRow() {
			public boolean CheckRow(HtmlRow row) throws Exception {
				Date tradeDate = DateUtil.getDateFromString(row.getCellValue(TradeFairPage.TradeDateColNo));
				return row.getCellValue(TradeFairPage.TradePeriodColNo).equals(period)
						&& tradeDate.getTime() >= startDate.getTime() 
						&& tradeDate.getTime() <= endDate.getTime();
			}
		});
		Assert.assertNull(result, "按复合条件搜索结果不对，不符合条件的行：" + tradeFairPage.printData(result));
		
		//点击重置按钮，验证查询条件被重置，表格显示所有交易场次
		tradeFairPage.getResetBtn().click();
		Driver.wait(loadTime);
		Assert.assertEquals(tradeFairPage.getTradePeriodSelect().getSelectedIndex(), 0, "查询条件交易周期未重置");
		Assert.assertEquals(tradeFairPage.getTradeStartDatePicker().getText(), "", "查询条件交易日期(开始)未重置");
		Assert.assertEquals(tradeFairPage.getTradeEndDatePicker().getText(), "", "查询条件交易日期(结束)未重置");
		Assert.assertEquals(tradeFairPage.getPager().getTotalCount(), count, "查询条件重置后未显示所有交易场次");
		
		//点击查询按钮，验证表格显示所有交易场次
		tradeFairPage.getQueryBtn().click();
		Driver.wait(loadTime);
		Assert.assertEquals(tradeFairPage.getPager().getTotalCount(), count, "未设置查询条件时点查询没有显示所有交易场次");
	}
	
	@Test(suiteName="Sep", groups="交易管理", priority=1, testName="创建交易场次-失败",
			description="字段验证和错误提示信息测试")
	public void Sep_TradeFairNew04() throws Exception{
		
	}
	
}
