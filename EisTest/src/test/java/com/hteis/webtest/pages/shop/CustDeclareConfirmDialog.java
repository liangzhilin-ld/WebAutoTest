package com.hteis.webtest.pages.shop;

import org.testng.Assert;

import com.hteis.webtest.entities.MonthDeclareData;
import com.hteis.webtest.selenium.*;

public class CustDeclareConfirmDialog extends HtmlPage{
	
	public HtmlElement getContainer(){
		return this.findElementByClass("ngdialog");
	}
	
	public HtmlButton getConfirmBtn(){
		return this.getContainer().findElementByCss(".modal-footer button:first-child").toHtmlButton();
	}
	
	public HtmlButton getCancelBtn(){
		return this.getContainer().findElementByCss(".modal-footer button:last-child").toHtmlButton();
	}
	
	/***交易场次名称*/
	public String getTradeFairName(){
		return this.getContainer().findElementByCss("div h5 b.ng-binding:first-child").getText().replace("交易场次名称：", "");
	}
	
	/***电压等级*/
	public String getVoltageLevel(){
		return this.getContainer().findElementByCss("div h5 b.ng-binding:last-child").getText().replace("电压等级：", "");
	}
	
	public HtmlMultiBodyTable getDeclareAmountTable(){
		return this.getContainer().findElementByTag("table").toHtmlMultiBodyTable();
	}
	
	public void verifyDeclareData(MonthDeclareData[] dataArr){
		HtmlMultiBodyTable table = this.getDeclareAmountTable();
		for(int i = 0; i < dataArr.length; i++){	
			MonthDeclareData data = dataArr[i];
			Assert.assertEquals(table.getCellValue(i+1, 1, 1), String.format("%d月",data.Month)); // 月份
			Assert.assertEquals(table.getCellValue(i+1, 1, 3), Float.toString(data.MonthAmount)); //分解电量
			Assert.assertEquals(table.getCellValue(i+1, 1, 5), Float.toString(data.DiffAmount)); // 申报电量(增量)
			Assert.assertEquals(Float.parseFloat(table.getCellValue(i+1, 2, 2)), data.TotalAmount); // 申报总量
			Assert.assertEquals(table.getCellValue(i+1, 2, 4), data.DiffPercent == null? "-" : Float.toString(data.DiffPercent)); // 增量比例
			Assert.assertEquals(table.getCellValue(i+1, 3, 2), data.LinkRatio == null? "-" : Float.toString(data.LinkRatio)); // 环比增幅
			Assert.assertEquals(table.getCellValue(i+1, 3, 2), data.YearToYearRatio == null? "-" : Float.toString(data.YearToYearRatio)); // 同比增幅
		}
	}
}
