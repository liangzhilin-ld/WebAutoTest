package com.hteis.webtest.pages;

import com.hteis.webtest.selenium.*;

public class CustListPage extends HtmlPage {
	
	public static final int custNameColNo = 3;
	
	public HtmlTable getCustTable(){
		return this.findElementByTag("table").toHtmlTable();
	}
}
