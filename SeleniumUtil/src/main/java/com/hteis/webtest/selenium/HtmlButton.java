package com.hteis.webtest.selenium;

import org.openqa.selenium.*;

public class HtmlButton extends HtmlElement {

	public HtmlButton(WebElement element) {
		super(element);
	}

	public void click() {
		this.webElement.click();
		this.afterAction();
	}
	
	public boolean isEnabled(){
		return this.webElement.isEnabled();
	}
}
