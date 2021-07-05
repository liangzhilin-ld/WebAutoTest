package com.hteis.webtest.selenium;

import org.openqa.selenium.WebElement;

public class HtmlInput extends HtmlElement {
	public HtmlInput(WebElement element) {
		super(element);
	}

	public void setText(String text) {
		this.webElement.clear();
		this.webElement.sendKeys(text);
		this.afterAction();
	}	
	
	public void input(String text){
		this.webElement.sendKeys(text);
		this.afterAction();
	}
	
	public void clear(){
		this.webElement.clear();
		this.afterAction();
	}

	public String getText(){
		return this.webElement.getAttribute("value");
	}
	
	public boolean isReadOnly(){
		return "true".equals(this.webElement.getAttribute("readonly"));
	}
}
