package com.hteis.webtest.pages.mgc;

import java.util.ArrayList;

import com.hteis.webtest.pages.*;
import com.hteis.webtest.selenium.*;

public class MgMgmtIndexPage extends PageBase {
	
	public static final int SelectColNo = 1;
	public static final int NameColNo = 4;
	public static final int EnNameColNo = 5;

	public HtmlTable getUnselectedTable(){
		return this.findElementByCss("div[ng-show='showLeft'] table").toHtmlTable();
	}
	
	/***未选择*/
	public HtmlButton getUnSelectedBtn(){
		return this.findElementByCss(".tabbable ul.nav li:first-child button").toHtmlButton();
	}
	
	/***已选择*/
	public HtmlButton getSelectedBtn(){
		return this.findElementByCss(".tabbable ul.nav li:last-child button").toHtmlButton();
	}
	
	/***添加按钮*/
	public HtmlButton getAddBtn(){
		return this.findElementByCss("div[ng-show='showLeft'] .page-list + button + button").toHtmlButton();
	}
	
	/***未选择页返回按钮*/
	public HtmlButton getUnselectBackBtn(){
		return this.findElementByCss("div[ng-show='showLeft'] .page-list + button").toHtmlButton();
	}
	
	/***删除按钮*/
	public HtmlButton getDelBtn(){
		return this.findElementByCss("div[ng-show='showRight'] .page-list + button + button").toHtmlButton();
	}
	
	/***选择页返回按钮*/
	public HtmlButton getSelectedBackBtn(){
		return this.findElementByCss("div[ng-show='showRight'] .page-list + button").toHtmlButton();
	}
	
	

	public HtmlTable getSelectedTable(){
		return this.findElementByCss("div[ng-show='showRight'] table").toHtmlTable();
	}
	
	public HtmlCheckBox getSelAllSelectedCk(){
		return this.getSelectedTable().findElementByCss("th input[type='checkbox']").toHtmlCheckBox();
	}
	
	public void selectIndex(String[] indexNames){
		HtmlTable table = this.getUnselectedTable();
		for(String name : indexNames){
			table.findRow(NameColNo, name).getCell(SelectColNo).findElementByTag("input").toHtmlCheckBox().check();
		}
	}
	
	public String[] getSelectedIndex(){
		HtmlTable table = this.getSelectedTable();
		ArrayList<String> names = new ArrayList<String>();
		for(HtmlRow row : table.getRows()){
			names.add(row.getCellValue(NameColNo));
		}
		
		return (String[])names.toArray(new String[]{});
	}
}
