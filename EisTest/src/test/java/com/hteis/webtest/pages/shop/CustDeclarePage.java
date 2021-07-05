package com.hteis.webtest.pages.shop;

import java.util.*;

import org.testng.Assert;

import com.hteis.webtest.common.DateUtil;
import com.hteis.webtest.entities.*;
import com.hteis.webtest.pages.PageBase;
import com.hteis.webtest.selenium.*;

public class CustDeclarePage extends PageBase {
	
	public HtmlTable getDeclareDataTable(){
		return this.findElementByCss("form table:first-child").toHtmlTable();
	}
	
	public HtmlMultiBodyTable getDeclareAmountTable(){
		return this.findElementByCss("form table:last-child").toHtmlMultiBodyTable();
	}
	
	/***交易场次名称*/
	public String getTradeFairName(){
		return this.findElementByCss("form div h5 b:first-child").getText().replace("交易场次名称：", "");
	}
	
	/***交易周期*/
	public String getTradeFairPeriod(){
		return this.findElementByCss("form div h5 b:last-child").getText().replace("交易周期：", "");
	}
	
	/***申报开始日期*/
	public Date getDeclareStartDate() throws Exception{
		return DateUtil.getDateFromString(this.getDeclareDataTable().getCell(1, 2).getText());
	}
	
	/***申报截至日期*/	
	public Date getDeclareEndDate() throws Exception{
		return DateUtil.getDateFromString(this.getDeclareDataTable().getCell(1, 4).getText());
	}
	
	/***电压等级*/	
	public String getVoltageLevel(){
		return this.getDeclareDataTable().getCell(2, 2).getText();
	}
	
	/***申报状态*/
	public String getDeclareStatus(){
		return this.getDeclareDataTable().getCell(2, 4).getText();
	}
	
	/***保存按钮*/
	public HtmlButton getSaveBtn(){
		return this.findElementByCss(".modal-footer button:first-child").toHtmlButton();
	}
	
	/***提交按钮*/
	public HtmlButton getSubmitBtn(){
		return this.findElementByCss(".modal-footer button:last-child").toHtmlButton();
	}
	
	public void setDiffAmount(float[] diffAmounts){
		HtmlMultiBodyTable table = this.getDeclareAmountTable();
		for(int i = 1; i <= diffAmounts.length; i++){
			table.getCell(i, 1, 5).findElementById("cdeRepElec").toHtmlInput().setText(Float.toString(diffAmounts[i - 1]));
		}
	}
	
	public void verifyDeclareData(MonthDeclareData[] dataArr){
		HtmlMultiBodyTable table = this.getDeclareAmountTable();
		for(int i = 0; i < dataArr.length; i++){	
			MonthDeclareData data = dataArr[i];
			Assert.assertEquals(table.getCellValue(i+1, 1, 1), String.format("%d月",data.Month)); // 月份
			Assert.assertEquals(table.getCellValue(i+1, 1, 3), Float.toString(data.MonthAmount)); //分解电量
			Assert.assertEquals(table.getCell(i+1, 1, 5).findElementById("cdeRepElec").toHtmlInput().getText(), Float.toString(data.DiffAmount)); // 申报电量(增量)
			Assert.assertEquals(Float.parseFloat(table.getCellValue(i+1, 2, 2)), data.TotalAmount); // 申报总量
			Assert.assertEquals(table.getCellValue(i+1, 2, 4), data.DiffPercent == null? "-" : Float.toString(data.DiffPercent)); // 增量比例
			Assert.assertEquals(table.getCellValue(i+1, 3, 2), data.LinkRatio == null? "-" : Float.toString(data.LinkRatio)); // 环比增幅
			Assert.assertEquals(table.getCellValue(i+1, 3, 2), data.YearToYearRatio == null? "-" : Float.toString(data.YearToYearRatio)); // 同比增幅
		}
	}
	
	public void verifyDeclareDataAfterSubmit(MonthDeclareData[] dataArr){
		HtmlMultiBodyTable table = this.getDeclareAmountTable();
		for(int i = 0; i < dataArr.length; i++){	
			MonthDeclareData data = dataArr[i];
			Assert.assertEquals(table.getCellValue(i+1, 1, 1), String.format("%d月",data.Month)); // 月份
			Assert.assertEquals(table.getCellValue(i+1, 1, 3), Float.toString(data.MonthAmount)); //分解电量
			Assert.assertEquals(table.getCellValue(i+1, 1, 5), Float.toString(data.DiffAmount)); // 申报电量(增量)
			Assert.assertEquals(Float.parseFloat(table.getCellValue(i + 1, 2, 2)), data.TotalAmount); // 申报总量
			Assert.assertEquals(table.getCellValue(i+1, 2, 4), data.DiffPercent == null? "-" : Float.toString(data.DiffPercent)); // 增量比例
			Assert.assertEquals(table.getCellValue(i+1, 3, 2), data.LinkRatio == null? "-" : Float.toString(data.LinkRatio)); // 环比增幅
			Assert.assertEquals(table.getCellValue(i+1, 3, 2), data.YearToYearRatio == null? "-" : Float.toString(data.YearToYearRatio)); // 同比增幅
		}
	}
	
}
