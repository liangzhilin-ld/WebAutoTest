package com.hteis.webtest.pages.cust;

import org.testng.*;

import com.hteis.webtest.common.DateUtil;
import com.hteis.webtest.entities.CustServiceData;
import com.hteis.webtest.pages.*;
import com.hteis.webtest.selenium.*;

public class CustServicePage extends PageBase {
	
	public static final int CompanyColNo = 2;
	public static final int UserTypeColNo = 3;
	public static final int IssueTypeColNo = 4;
	public static final int DescriptionColNo = 5;
	public static final int CustNameColNo = 6;
	public static final int CreateDateTimeColNo = 7;
	public static final int ProcessPersonColNo = 8;
	public static final int ProcessDateTimeColNo = 9;
	public static final int StatusColNo = 10;
	public static final int OpsColNo = 11;
	
	
	
	public HtmlButton getNewBtn(){
		return this.findElementByCss("div.top-button button").toHtmlButton();
	}
	
	public HtmlTable getCustServiceTable(){
		return this.findElementByTag("table").toHtmlTable();
	}
	
	public HtmlNgPager getPager(){
		return this.findElementByClass("page-list").toHtmlNgPager();
	}
	
	public HtmlInput getCompanyInput(){
		return this.findElementById("enterName").toHtmlInput();
	}
	
	public HtmlSelect getUserTypeSelect(){
		return this.findElementByCss("select[ng-model='query.userType']").toHtmlSelect();
	}
	
	public HtmlSelect getIssueTypeSelect(){
		return this.findElementByCss("select[ng-model='query.classification'").toHtmlSelect();
	}
	
	public HtmlInput getCustNameInput(){
		return this.findElementById("custName").toHtmlInput();
	}
	
	public HtmlWDatePicker getCreateStartTimePicker(){
		return this.findElementById("createTime_start").toHtmlWDatePicker();
	}
	
	public HtmlWDatePicker getCreateEndTimePicker(){
		return this.findElementById("createTime_end").toHtmlWDatePicker();
	}
	
	public HtmlWDatePicker getProcessStartTimePicker(){
		return this.findElementById("modifyTime_start").toHtmlWDatePicker();
	}
	
	public HtmlWDatePicker getProcessEndTimePicker(){
		return this.findElementById("modifyTime_end").toHtmlWDatePicker();
	}
	
	public HtmlSelect getStatusSelect(){
		return this.findElementByCss("select[ng-model='query.handleStatus']").toHtmlSelect();
	}
	
	public HtmlButton getQueryBtn(){
		return this.findElementByCss("form button:last-child").toHtmlButton();		
	}
	
	public HtmlButton getResetBtn(){
		return this.findElementByCss("form button:first-child").toHtmlButton();		
	}
	
	public void verifyData(CustServiceData data){
		HtmlRow row = this.getCustServiceTable().findRow(DescriptionColNo, data.Description);
		Assert.assertEquals(row.getCellValue(CompanyColNo), data.Company);
		Assert.assertEquals(row.getCellValue(UserTypeColNo), data.UserType);
		Assert.assertEquals(row.getCellValue(IssueTypeColNo), data.IssueType);
		Assert.assertEquals(row.getCellValue(DescriptionColNo), data.Description);
		Assert.assertEquals(row.getCellValue(CustNameColNo), data.CustName);
		Assert.assertEquals(row.getCellValue(CreateDateTimeColNo).substring(0, 10), DateUtil.getCNDateStr(data.CreateDateTime));
		Assert.assertEquals(row.getCellValue(ProcessPersonColNo), data.ProcessPerson);
		Assert.assertEquals(row.getCellValue(ProcessDateTimeColNo).substring(0, 10), DateUtil.getCNDateStr(data.ProcessDateTime));
		Assert.assertEquals(row.getCellValue(StatusColNo), data.Status);
	}
	
	public String printData(HtmlRow row){	
		
		String rowData = "";
		if(row == null){
			return rowData;
		}
		
		String format = "%s:%s; ";
		rowData += String.format(format, "单位名称", row.getCellValue(CompanyColNo));
		rowData += String.format(format, "用户类型", row.getCellValue(UserTypeColNo));
		rowData += String.format(format, "问题类型", row.getCellValue(IssueTypeColNo));
		rowData += String.format(format, "问题描述", row.getCellValue(DescriptionColNo));
		rowData += String.format(format, "客户姓名", row.getCellValue(CustNameColNo));
		rowData += String.format(format, "请求记录时间", row.getCellValue(CreateDateTimeColNo));
		rowData += String.format(format, "处理人", row.getCellValue(ProcessPersonColNo));
		rowData += String.format(format, "处理时间", row.getCellValue(ProcessDateTimeColNo));
		rowData += String.format(format, "状态", row.getCellValue(StatusColNo));
		
		return rowData;
	}
}
