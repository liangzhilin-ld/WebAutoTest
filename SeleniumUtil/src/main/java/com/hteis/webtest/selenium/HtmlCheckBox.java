package com.hteis.webtest.selenium;

import org.openqa.selenium.WebElement;

public class HtmlCheckBox extends HtmlElement {

	public HtmlCheckBox(WebElement element) {
		super(element);
	}

	public void check() {
		if(!this.isChecked()){
			this.webElement.click();
			this.afterAction();
		}
	}
	
	public void unCheck() {
		if(this.isChecked()){
			this.webElement.click();
			this.afterAction();
		}
	}
	
	public boolean isChecked(){
		return this.webElement.isSelected();
	}
}