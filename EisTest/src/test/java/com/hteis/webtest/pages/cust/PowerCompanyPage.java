package com.hteis.webtest.pages.cust;

import java.util.ArrayList;
import java.util.Date;

import org.testng.*;
import com.hteis.webtest.entities.*;
import com.hteis.webtest.pages.PageBase;
import com.hteis.webtest.selenium.*;

/***发电企业管理页面*/
public class PowerCompanyPage extends PageBase {
	
	public static final int NameColNo = 2;
	public static final int PowerTypeColNo = 3;
	public static final int CompanyTypeColNo = 4;
	public static final int DistrictColNo = 5;
	public static final int ContactNameColNo = 6;
	public static final int YearlyPowerGenColNo = 7;
	public static final int StatusColNo = 8;
	public static final int OpsColNo = 9;

	public HtmlTable getPowerCompanyTable(){
		return this.findElementByTag("table").toHtmlTable();
	}
	
	public HtmlNgPager getPager(){
		return this.findElementByClass("page-list").toHtmlNgPager();
	}
	
	/***客户名称搜索输入框*/
	public HtmlInput getNameInput(){
		return this.findElementById("powerGenEntName").toHtmlInput();
	}
	
	/***查询-发电类型*/
	public HtmlSelect getPowerTypeSelect(){
		return this.findElementByCss("label[for='powerType'] + div select").toHtmlSelect();
	}
	
	/***查询-企业性质*/
	public HtmlSelect getCompanyTypeSelect(){
		return this.findElementByCss("label[for='enterCharacter'] + div select").toHtmlSelect();
	}
	
	/***查询-企业规模*/
	public HtmlSelect getCompanyScaleSelect(){
		return this.findElementByCss("label[for='enterScale'] + div select").toHtmlSelect();
	}
	
	/***查询按钮*/
	public HtmlButton getQueryBtn(){
		return this.findElementByCss("form button:last-child").toHtmlButton();
	}
	
	/***重置按钮*/
	public HtmlButton getResetBtn(){
		return this.findElementByCss("form button:first-child").toHtmlButton();
	}
	
	/***创建发电企业按钮*/
	public HtmlButton getNewBtn(){
		return this.findElementByCss(".top-button button").toHtmlButton();
	}
	
	/***打开详情对话框*/
	public PowerCompanyDetailsDialog openDetailsDialog(String companyName){
		HtmlRow row = this.getPowerCompanyTable().findRow(NameColNo, companyName);
		row.getCell(OpsColNo).findElementByLinkText("详情").click();
		return new PowerCompanyDetailsDialog();
	}
	
	/***打开编辑对话框*/
	public PowerCompanyNewDialog openEditDialog(String companyName){
		HtmlRow row = this.getPowerCompanyTable().findRow(NameColNo, companyName);
		row.getCell(OpsColNo).findElementByLinkText("编辑").click();
		return new PowerCompanyNewDialog();
	}
	
	/***提交一个发电企业*/
	public Date submitCompany(String companyName){
		HtmlRow row = this.getPowerCompanyTable().findRow(NameColNo, companyName);
		row.getCell(OpsColNo).findElementByLinkText("提交").click();
		return new Date();
	}
	
	public void verifyData(PowerCompanyData data){
		HtmlRow row = this.getPowerCompanyTable().findRow(NameColNo, data.MainInfo.Name);
		Assert.assertEquals(row.getCellValue(PowerTypeColNo), data.MainInfo.PowerType);
		Assert.assertEquals(row.getCellValue(CompanyTypeColNo), data.MainInfo.CompanyType);
		Assert.assertEquals(row.getCellValue(DistrictColNo), data.MainInfo.District);
		Assert.assertEquals(row.getCellValue(ContactNameColNo), data.ContactInfo.Name);
		Assert.assertEquals(row.getCellValue(YearlyPowerGenColNo), Integer.toString(data.EleInfo.YearlyPowerGen));
		Assert.assertEquals(row.getCellValue(StatusColNo), data.Status);		
	}
	
	/***验证可用的操作*/
	public void verifyOperations(String companyName, String[] ops){
		HtmlRow row = this.getPowerCompanyTable().findRow(NameColNo, companyName);
		ArrayList<String> operations = new ArrayList<String>();
		
		for(HtmlElement link : row.getCell(OpsColNo).findElementsByTag("a")){
			operations.add(link.getText());
		}
		
		Assert.assertEquals((String[])operations.toArray(new String[]{}), ops, "当前用户对发电企业的可用操作不正确");
	}
	
	public String printData(HtmlRow row){	
		
		String rowData = "";
		if(row == null){
			return rowData;
		}
		
		String format = "%s:%s; ";
		rowData += String.format(format, "发电企业名称", row.getCellValue(NameColNo));
		rowData += String.format(format, "发电类型", row.getCellValue(PowerTypeColNo));
		rowData += String.format(format, "企业性质", row.getCellValue(CompanyTypeColNo));
		rowData += String.format(format, "区域", row.getCellValue(DistrictColNo));
		rowData += String.format(format, "主联系人", row.getCellValue(ContactNameColNo));
		rowData += String.format(format, "年发电量", row.getCellValue(YearlyPowerGenColNo));
		rowData += String.format(format, "状态", row.getCellValue(StatusColNo));
		
		return rowData;
	}
}
