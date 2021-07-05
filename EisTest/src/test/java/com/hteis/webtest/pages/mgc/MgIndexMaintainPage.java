package com.hteis.webtest.pages.mgc;

import java.util.ArrayList;

import org.testng.Assert;

import com.hteis.webtest.entities.MgModelData;
import com.hteis.webtest.pages.*;
import com.hteis.webtest.selenium.*;

/***创建监控模型的最后一个页面*/
public class MgIndexMaintainPage extends PageBase {
	
	public static final int SelectColNo = 1;
	public static final int NameColNo = 4;
	public static final int EnNameColNo = 5;

	public HtmlTable getUnselectedTable(){
		//return this.findElementByCss("div[ng-show='showLeft'] table").toHtmlTable();
		return this.findElementByTag("table").toHtmlTable();
	}
	
	/***未选择*/
	public HtmlLink getUnSelectedTab(){
		return this.findElementByCss(".tabbable ul.nav li:last-child a").toHtmlLink();
	}
	
	/***已选择*/
	public HtmlLink getSelectedTab(){
		return this.findElementByCss(".tabbable ul.nav li:first-child a").toHtmlLink();
	}
	
	/***添加按钮*/
	public HtmlButton getAddBtn(){
		return this.findElementByCss(".page-list + button + button").toHtmlButton();
	}	
	
	/***删除按钮*/
	public HtmlButton getDelBtn(){
		return this.findElementByCss(".page-list + button + button").toHtmlButton();
	}	
	
	/***返回按钮*/
	public HtmlButton getBackBtn(){
		return this.findElementByCss(".page-list + button").toHtmlButton();
	}	

	public HtmlTable getSelectedTable(){
		//return this.findElementByCss("div[ng-show='showRight'] table").toHtmlTable();
		return this.findElementByTag("table").toHtmlTable();
	}
	
	public HtmlCheckBox getSelAllSelectedCk(){
		return this.getSelectedTable().findElementByCss("th input[type='checkbox']").toHtmlCheckBox();
	}
	
	public void selectIndex(ArrayList<String> indexNames){
		HtmlTable table = this.getUnselectedTable();
		for(String name : indexNames){
			table.findRow(NameColNo, name).getCell(SelectColNo).findElementByTag("input").toHtmlCheckBox().check();
		}
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
	
	public void verifySelectedIndex(MgModelData modelData){
		this.getSelectedTab().click();
		Driver.wait(1000);
		String mgFullName = modelData.getFullName();
		String[] indexNames = new String[modelData.indexNames.size()];
		int i = 0;
		for(String indexName : modelData.indexNames){
			indexNames[i] = mgFullName + "/" + indexName;
			i++;
		}
		Assert.assertEquals(this.getSelectedIndex(), indexNames, "指标添加失败");
	}
}
