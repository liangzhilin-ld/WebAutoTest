package com.hteis.webtest.pages;

import com.hteis.webtest.selenium.*;

public class CustContractPage extends PageBase {

	public static final int ContractNameColNo = 3;
	public static final int SignDateColNo = 4;
	public static final int ContractStartDateColNo = 5;
	public static final int ContractEndDateColNo = 6;
	public static final int StatusColNo = 7;
	public static final int OpsColNo = 8;
	
	public HtmlTable getContractTable(){
		return this.findElementByTag("table").toHtmlTable();
	}
	
	public HtmlNgPager getPager(){
		return this.findElementByClass("page-list").toHtmlNgPager();
	}	
}
