package com.hteis.webtest.selenium;

import org.openqa.selenium.WebElement;

public class HtmlLink extends HtmlElement {

	public HtmlLink(WebElement element) {
		super(element);
	}

	public void click() {
		this.webElement.click();
		this.afterAction();
	}
}
