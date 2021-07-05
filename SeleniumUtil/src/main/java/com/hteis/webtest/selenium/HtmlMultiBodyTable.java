package com.hteis.webtest.selenium;

import org.openqa.selenium.WebElement;

/***适用于包含多个tbody的table*/
public class HtmlMultiBodyTable extends HtmlElement {
	
	public HtmlMultiBodyTable(WebElement element) {
		super(element);
	}
	
	/***获取tbody的数量*/
	public int getBodyCount(){
		return this.findElementsByTag("tbody").size();
	}
	
	/***获取第bodyNo个tbody的第rowNo行第colNo列的单元格*/
	public HtmlElement getCell(int bodyNo, int rowNo, int colNo){
		return this.findElementByCss(String.format("tbody:nth-child(%d) tr:nth-child(%d) td:nth-child(%d)", bodyNo, rowNo, colNo));
	}
	
	/***获取第bodyNo个tbody的第rowNo行第colNo列内的文本*/
	public String getCellValue(int bodyNo, int rowNo, int colNo){
		return this.getCell(bodyNo, rowNo, colNo).getText();
	}
	
	
}
