package com.hteis.webtest.pages.trade;

import org.testng.Assert;

import com.hteis.webtest.common.DateUtil;
import com.hteis.webtest.entities.*;
import com.hteis.webtest.pages.PageBase;
import com.hteis.webtest.selenium.*;

public class TradeFairPage extends PageBase {
	
	public static final int TradeDateColNo = 2;
	public static final int NameColNo = 3;
	public static final int TradeTypeColNo = 4;
	public static final int TradePeriodColNo = 5;
	public static final int TradeStartDateColNo = 6;
	public static final int TradeEndDateColNo = 7;
	public static final int SupplyCountColNo = 8;
	public static final int BuyerCountColNo = 9;
	public static final int DeclareStartDateColNo = 10;
	public static final int DeclareEndDateColNo = 11;
	public static final int StatusColNo = 12;
	public static final int OpsColNo = 13;
	
	/***交易场次表格*/
	public HtmlTable getTradeFairTable(){
		return this.findElementByTag("table").toHtmlTable();
	}
	
	/***翻页控件*/
	public HtmlNgPager getPager(){
		return this.findElementByClass("page-list").toHtmlNgPager();
	}
	
	/***新增链接*/
	public HtmlLink getAddLink(){
		return this.findElementByCss("a.btn-new").toHtmlLink();
	}
	
	/***查询 - 交易周期选择框*/
	public HtmlSelect getTradePeriodSelect(){
		return this.findElementByCss("label[for='cxtnCyc'] + div select").toHtmlSelect();
	}
	
	/***查询 - 交易日期-开始*/
	public HtmlWDatePicker getTradeStartDatePicker(){
		return this.findElementById("cxFirstDate").toHtmlWDatePicker();
	}
	
	/***查询 - 交易日期-结束*/
	public HtmlWDatePicker getTradeEndDatePicker(){
		return this.findElementById("cxLastDate").toHtmlWDatePicker();
	}
	
	/***打开交易场次编辑或查看*/
	public TradeFairEditPage openTradeFair(String tradeFairName){
		HtmlRow row = this.getTradeFairTable().findRow(TradeFairPage.NameColNo, tradeFairName);
		row.getCell(TradeFairPage.NameColNo).findElementByTag("a").click();
		Driver.wait(1000);
		return new TradeFairEditPage();
	}
	
	/***查询按钮*/
	public HtmlButton getQueryBtn(){
		return this.findElementByCss(".select-sql button:last-child").toHtmlButton();
	}
	
	/***重置按钮*/
	public HtmlButton getResetBtn(){
		return this.findElementByCss(".select-sql button:first-child").toHtmlButton();
	}
	
	public void deleteTradeFair(String tradeFairName){
		HtmlRow row = this.getTradeFairTable().findRow(NameColNo, tradeFairName);
		row.getCell(TradeFairPage.NameColNo).findElementByTag("a").click();
		Driver.wait(1000);
		TradeFairEditPage editPage = new TradeFairEditPage();
		editPage.getDelBtn().click();
		editPage.ClickConfirm();
		editPage.verifyAlert("删除成功");
	}
	
	public void verifyTradeFairData(TradeFairData tradeFairData){
		HtmlRow row = this.getTradeFairTable().findRow(NameColNo, tradeFairData.Name);
		Assert.assertEquals(row.getCellValue(TradeDateColNo), DateUtil.getCNDateStr(tradeFairData.TradeDate));
		Assert.assertEquals(row.getCellValue(TradeTypeColNo),  tradeFairData.TradeType);
		Assert.assertEquals(row.getCellValue(TradePeriodColNo),  tradeFairData.TradePeriod);
		
		Assert.assertEquals(row.getCellValue(StatusColNo),  tradeFairData.Status);
		if(tradeFairData.TradeType.equals("协商")){
			Assert.assertEquals(row.getCellValue(TradeStartDateColNo),  DateUtil.getCNDateStr(tradeFairData.TradeStartDateTime));
			Assert.assertEquals(row.getCellValue(TradeEndDateColNo),  DateUtil.getCNDateStr(tradeFairData.TradeEndDateTime));
			Assert.assertEquals(row.getCellValue(SupplyCountColNo),  "-");
			Assert.assertEquals(row.getCellValue(BuyerCountColNo),  "-");
			Assert.assertEquals(row.getCellValue(DeclareStartDateColNo),  "-");
			Assert.assertEquals(row.getCellValue(DeclareEndDateColNo),  "-");
		} else {
			Assert.assertEquals(row.getCellValue(TradeStartDateColNo),  DateUtil.getCNTimeStr(tradeFairData.TradeStartDateTime));
			Assert.assertEquals(row.getCellValue(TradeEndDateColNo),  DateUtil.getCNTimeStr(tradeFairData.TradeEndDateTime));
			Assert.assertEquals(row.getCellValue(SupplyCountColNo),  Integer.toString(tradeFairData.SupplyCount));
			Assert.assertEquals(row.getCellValue(BuyerCountColNo),  Integer.toString(tradeFairData.BuyerCount));
			Assert.assertEquals(row.getCellValue(DeclareStartDateColNo),  DateUtil.getCNDateStr(tradeFairData.DeclareStartDate));
			Assert.assertEquals(row.getCellValue(DeclareEndDateColNo),  DateUtil.getCNDateStr(tradeFairData.DeclareEndDate));
		}
		
	}
	
	public String printData(HtmlRow row){
		String rowData = "";
		if(row == null){
			return rowData;
		}
		
		String format = "%s:%s; ";
		rowData += String.format(format, "交易日期", row.getCellValue(TradeDateColNo));
		rowData += String.format(format, "场次名称", row.getCellValue(NameColNo));
		rowData += String.format(format, "交易品种", row.getCellValue(TradeTypeColNo));
		rowData += String.format(format, "交易周期", row.getCellValue(TradePeriodColNo));
		rowData += String.format(format, "交易开始时间", row.getCellValue(TradeStartDateColNo));
		rowData += String.format(format, "交易结束时间", row.getCellValue(TradeEndDateColNo));
		rowData += String.format(format, "供应方数量", row.getCellValue(SupplyCountColNo));
		rowData += String.format(format, "需求方数量", row.getCellValue(BuyerCountColNo));
		rowData += String.format(format, "申报开始时间", row.getCellValue(DeclareStartDateColNo));
		rowData += String.format(format, "申报截止时间", row.getCellValue(DeclareEndDateColNo));
		rowData += String.format(format, "状态", row.getCellValue(StatusColNo));
		
		return rowData;
	}
}
