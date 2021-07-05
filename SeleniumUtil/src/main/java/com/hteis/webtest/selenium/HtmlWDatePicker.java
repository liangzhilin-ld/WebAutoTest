package com.hteis.webtest.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class HtmlWDatePicker extends HtmlInput{

	public HtmlWDatePicker(WebElement element) {
		super(element);
	}

	public void selectToday(){
		this.openDatePick();
		Driver.webDriver.findElement(By.id("dpTodayInput")).click();	
		Driver.webDriver.switchTo().defaultContent();
		this.switchBack();
	}
	
	public void selectNextYearToday(){
		this.openDatePick();
		Driver.webDriver.findElement(By.cssSelector("#dpTitle div:nth-child(5) > a")).click();
		Driver.webDriver.findElement(By.cssSelector("td.Wselday")).click();
		this.switchBack();		
	}
	
	private void openDatePick(){
		this.click();
		WebElement frame = Driver.webDriver.findElement(By.tagName("iframe"));
		Driver.webDriver.switchTo().frame(frame);
	}
	
	private void switchBack(){
		Driver.webDriver.switchTo().defaultContent();
	}
}
