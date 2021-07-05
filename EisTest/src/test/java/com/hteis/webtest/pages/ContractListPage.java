package com.hteis.webtest.pages;

import com.hteis.webtest.selenium.*;

public class ContractListPage extends PageBase {
	
	public static final int ContractIdColNo = 2;
	public static final int CustNameColNo = 3;
	public static final int StatusColNo = 7;
	public static final int OpsColNo = 8;
	
	public HtmlButton getNewContractBtn(){
		return this.findElementByLinkText("起草合同").toHtmlButton();
	}
	
	public HtmlTable getContractTable(){
		return this.findElementByTag("table").toHtmlTable();
	}
}
