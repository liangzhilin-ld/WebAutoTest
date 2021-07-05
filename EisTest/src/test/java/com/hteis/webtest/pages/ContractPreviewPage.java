package com.hteis.webtest.pages;

import com.hteis.webtest.selenium.*;

public class ContractPreviewPage extends HtmlPage {
	
	public String getTitle(){
		return this.findElementByTag("h2").getText();
	}
}
