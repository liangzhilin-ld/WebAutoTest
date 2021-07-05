package com.hteis.webtest.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class HtmlRow extends HtmlElement {

	public HtmlRow(WebElement element) {
		super(element);
	}
	
	public String getCellValue(int colNo){
		return this.getCell(colNo).getText();
	}
	
	public HtmlElement getCell(int colNo){
		return new HtmlElement(this.webElement.findElement(By.cssSelector(String.format("td:nth-child(%d)", colNo))));
	}
}
