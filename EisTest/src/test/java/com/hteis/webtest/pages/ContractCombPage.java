package com.hteis.webtest.pages;

import com.hteis.webtest.selenium.*;

public class ContractCombPage extends PageBase{
	
	private static final int idColNo = 2;

	/***新增按钮*/
	public HtmlButton getNewBtn(){
		return this.findElementByTag("button").toHtmlButton();
	}
	
	/***合同套餐表格*/
	public HtmlTable getContractCombTable(){
		return this.findElementByTag("table").toHtmlTable();
	}
	
	/***表格翻页控件组*/
	public HtmlNgPager getPager(){
		return this.findElementByCss("div.page-list").toHtmlNgPager();
	}
	
	/***含停用单选框*/
	public HtmlCheckBox getIncludeDisabledCk(){
		return this.getContractCombTable().findElementByCss("th input[type='checkbox']").toHtmlCheckBox();
	}
	
	public void previewCombo(String comboId){
		this.getContractCombTable().findRow(idColNo, comboId).findElementByLinkText("预览").click();
	}
}
